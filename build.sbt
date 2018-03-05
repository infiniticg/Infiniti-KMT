import java.util.Properties

import complete.DefaultParsers._
import com.typesafe.sbt.license.LicenseReport

import scala.collection.JavaConverters._
import sbt.io.Using

lazy val learningedge_config = project in file("Dev/learningedge-config")

lazy val allPlugins = LocalProject("allPlugins")

val legacyPaths = Seq(
  javaSource in Compile := baseDirectory.value / "src",
  javaSource in Test := baseDirectory.value / "test",
  unmanagedResourceDirectories in Compile := (baseDirectory.value / "resources") :: Nil,
  unmanagedSourceDirectories in Compile := (javaSource in Compile).value :: Nil,
  unmanagedSourceDirectories in Test := (javaSource in Test).value :: Nil
)

lazy val equellaserver = (project in file("Source/Server/equellaserver")).enablePlugins(JPFRunnerPlugin)

lazy val adminTool = (project in file("Source/Server/adminTool")).settings(legacyPaths).dependsOn(
  platformSwing,
  platformEquella,
  LocalProject("com_tle_webstart_admin"),
  LocalProject("adminConsoleJar")
)

lazy val conversion = (project in file("Source/Server/conversion")).settings(legacyPaths).dependsOn(
  platformCommon
)

lazy val UpgradeInstallation = (project in file("Source/Tools/UpgradeInstallation")).settings(legacyPaths).dependsOn(
  platformCommon,
  platformEquella,
  log4jCustom
)

lazy val UpgradeManager = (project in file("Source/Tools/UpgradeManager")).settings(legacyPaths).dependsOn(platformCommon, platformEquella, log4jCustom)

lazy val Installer = (project in file("Installer")).settings(legacyPaths).dependsOn(platformCommon, platformSwing, platformEquella, UpgradeManager)

lazy val equella = (project in file(".")).enablePlugins(JPFScanPlugin, JarSignerPlugin, GitVersioning)
  .aggregate(equellaserver, allPlugins, adminTool, Installer,
    UpgradeManager, conversion, UpgradeInstallation, learningedge_config)

buildConfig in ThisBuild := Common.buildConfig

oracleDriverJar in ThisBuild := {
  val c = buildConfig.value
  if (c.hasPath("build.oraclejar")) {
    Some(file(c.getString("build.oraclejar")))
  } else None
}

name := "Equella"

equellaMajorMinor in ThisBuild := "6.6"
equellaStream in ThisBuild := "Beta"
equellaBuild in ThisBuild := buildConfig.value.getString("build.buildname")

git.useGitDescribe := true

val TagRegex = """(.*)-(.*)-(\d*)-(.*)""".r
git.gitTagToVersionNumber := {
  val streamName = equellaStream.value
  val majorMinor = equellaMajorMinor.value
  val buildName = equellaBuild.value

  {
    case TagRegex(_, _, v, sha) => Some(EquellaVersion(majorMinor, s"$streamName.$buildName", v.toInt, sha).fullVersion)
    case _ => None
  }
}

equellaVersion in ThisBuild := EquellaVersion(version.value)

versionProperties in ThisBuild := {
  val eqVersion = equellaVersion.value
  val props = new Properties
  props.putAll(
    Map("version.mm" -> eqVersion.majorMinor,
      "version.mmr" -> s"${eqVersion.majorMinor}.r${eqVersion.commits}",
      "version.display" -> s"${eqVersion.majorMinor}-${eqVersion.releaseType}",
      "version.commit" -> eqVersion.sha).asJava)
  val f = target.value / "version.properties"
  IO.write(props, "version", f)
  f
}

updateLicenses := {
  val ourOrg = organization.value
  val serverReport = (updateLicenses in equellaserver).value
  val plugsinReports = updateLicenses.all(ScopeFilter(inAggregates(allPlugins))).value
  val allLicenses = (plugsinReports.flatMap(_.licenses) ++ serverReport.licenses)
    .groupBy(_.module).values.map(_.head).filterNot(_.module.organization == ourOrg)
  LicenseReport(allLicenses.toSeq, serverReport.orig)
}

writeLanguagePack := {
  IO.withTemporaryDirectory { dir =>
    val allProps = langStrings.all(ScopeFilter(inAggregates(allPlugins, includeRoot = false))).value
      .flatten.groupBy(ls => (ls.group, ls.xml))
      .map { case ((g, xml), lss) =>
        val fname = g + (if (xml) ".xml" else ".properties")
        val f = dir / fname
        val p = new SortedProperties()
        lss.foreach(ls => p.putAll(ls.strings.asJava))
        Using.fileOutputStream()(f) { os =>
          if (xml) p.storeToXML(os, "") else p.store(os, "")
        }
        (f, fname)
      }
    val outZip = target.value / "reference-language-pack.zip"
    sLog.value.info(s"Writing ${outZip.absolutePath}")
    IO.zip(allProps, outZip)
    outZip
  }
}

aggregate in dumpLicenseReport := false

cancelable in Global := true

val pluginAndLibs = Def.task {
  val bd = baseDirectory.value
  val jpfLibs = jpfLibraryJars.value
  (bd, jpfLibs)
}

mergeJPF := {
  val adminConsole = false
  val args = spaceDelimited("<arg>").parsed
  val _allPluginDirs = pluginAndLibs.all(ScopeFilter(inAggregates(allPlugins, includeRoot = false))).value
  val extensionsOnly = (baseDirectory.value / "Source/Plugins/Extensions" * "*" / "plugin-jpf.xml").get
  val allPluginDirs = _allPluginDirs ++ (extensionsOnly.map(f => (f.getParentFile, Seq.empty)))
  if (args.isEmpty)
  {
    val plugins = PluginRefactor.findPluginsToMerge(allPluginDirs, adminConsole = adminConsole)
    println(s"mergeJPF <ID> ${plugins.mkString(" ")}")
  } else {
    val newPlugin = args.head
    val basePlugin = baseDirectory.value / "Source/Plugins"
    PluginRefactor.mergePlugins(allPluginDirs, basePlugin, newPlugin, args.tail, adminConsole = adminConsole)
  }
}

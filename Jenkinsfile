pipeline {
agent any
stages{
        stage('Build'){
            steps {
                sh 'chmod +x dockerbuild.sh'
                sh './dockerbuild.sh'
            }
        }
    }
}

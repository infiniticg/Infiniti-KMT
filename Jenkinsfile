pipeline {
agent any
stages{
        stage('Build'){
            steps {
                sh 'chmod +x dockerbuild.sh'
                sh './dockerbuild.sh'
            }
        }
        stage('Push to ECR'){
            steps {
                sh 'chmod +x dockerpush.sh'
                sh './dockerpush.sh'
            }
        }
    }
}

pipeline {
   agent any
   stages {
      stage ('Build') {
         when {
            branch 'wip-1.16.3'
         }
         steps {
            sh "rm -rf build/libs/"
            sh "chmod +x gradlew"
            sh "./gradlew clean --stacktrace"
            sh "./gradlew publishTerraform --refresh-dependencies --stacktrace"

            archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
         }
      }
   }
}

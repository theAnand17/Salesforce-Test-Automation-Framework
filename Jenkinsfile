pipeline {
    agent any

    tools {
        // Change 'maven' to the name of the Maven installation in your Jenkins Global Tool Configuration
        // If Maven is in your PATH, you can remove this 'tools' block.
        maven 'maven' 
        // jdk 'jdk19' // Uncomment and set if you have a specific JDK configured in Jenkins
    }

    parameters {
        string(name: 'APP_BRANCH', defaultValue: 'master', description: 'Branch to build')
        choice(name: 'AAP_ENVIRONMENT', choices: ['QA', 'Stage', 'Dev'], description: 'Target Environment (matches environment.yaml)')
        string(name: 'APP_ENV', defaultValue: 'Test', description: 'Environment Scope')
        string(name: 'TAGS', defaultValue: '@Examples', description: 'Cucumber Tags to run (e.g. @Salesforce, @BoBo)')
        string(name: 'NUMBER_OF_THREADS', defaultValue: '1', description: 'Number of parallel threads')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    def mavenCmd = "mvn clean test " +
                        "-Denvironment=${params.AAP_ENVIRONMENT} " +
                        "-Denv=${params.APP_ENV} " +
                        "-Dcucumber.filter.tags=\"${params.TAGS}\" " +
                        "-Ddataproviderthreadcount=${params.NUMBER_OF_THREADS} " +
                        "allure:report"
                    
                    if (isUnix()) {
                        sh mavenCmd
                    } else {
                        // For Windows Jenkins Agents
                        bat mavenCmd
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // Requires 'Allure Jenkins Plugin' installed
                 try {
                    allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
                } catch (Exception e) {
                    echo "Allure plugin not configured or failed: ${e.getMessage()}"
                }
            }
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/logs/**, target/site/**', fingerprint: true
        }
    }
}

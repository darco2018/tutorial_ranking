pipeline {

    options { skipDefaultCheckout() }

    agent {

        docker {
            image 'maven:3.6.0-jdk-8-alpine'
            args '--rm  -v /root/.m2:/root/.m2 -p 8080:8080'
        }
/*
        dockerfile {
            filename 'Dockerfile.build'
            dir 'build'
            customWorkspace '/some/other/path'
            label 'my-defined-label'
            additionalBuildArgs  '--build-arg version=1.0.2'
            args '-v /tmp:/tmp'
            registryUrl 'https://myregistry.com/'
            registryCredentialsId 'myPredefinedCredentialsInJenkins'
        }*/
    }
    // withEnv
    environment {
        LOG_ABS_PATH='/var/log/'
        MIGRATION_ABS_PATH='/var/migration/'
    }

    stage('Preparation') {
        steps {
            echo 'Preparation...'
            deleteDir()
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [],
                      submoduleCfg: [], userRemoteConfigs: [[url: 'ustrd@172.17.0.1:IdeaProjects/tutorial_ranking']]])
            sh 'printenv'
        }
    }

    stages {
        stage('run-parallel-branches') {
            steps {
                parallel(
                        a: {
                            echo "Tests on Linux"
                        },
                        b: {
                            echo "Tests on Windows"
                        }
                )
            }
        }

/*
        stage('Build') {
            steps {
                echo 'Building...'
                //echo 'Database must be up...' ??!!
                //sh './scripts/run.sh'
                sh 'mvn -B -DskipTests clean package' // -B run in nRun in non-interactive (batch) mode (disables output color)
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    // nie pownien image jako artifact byc ?!
                    archiveArtifacts 'docker/*jar'
                }
            }
        }
        //place more complex build steps (particularly for stages consisting of 2 or more steps) into separate shell script files like the deliver.sh file.
        stage('Deliver - Staging') {
            steps {
                echo 'Deliver - Staging...'
                echo 'Running mvn install - putting JAR to Jenkins local Maven repo...'
                echo 'Extracting the JARs name...'
                echo 'Runing the JAR http://localhost:8080/  ?!...'


                sh './jenkins/deliver.sh'



                //input message: 'Finished using the web site? (Click "Proceed" to continue)'

                //sh 'sleep 30' not working - app still running
                //sh 'curl -X POST localhost:8080/actuator/shutdown'
                //sh './deploy staging'
                //sh './run-smoke-tests'
            }
        }

        stage('Release ?!') {
            steps {
                echo 'Releasing...'

            }
        }

        stage('Sanity check') {
            steps {
                echo 'Sanity check...'
                //input "Does the staging environment look ok?"
            }
        }

        stage('Deploy - Production') {
            steps {
                echo 'Deploy - Staging...'
                //sh './deploy production'
            }
        }
    }
    */

    }
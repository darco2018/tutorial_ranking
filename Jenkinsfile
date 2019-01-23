pipeline {
    // Blocks must only consist of Sections, Directives, Steps, or assignment statements
    //Sections contain one or more Directives or Steps.
    agent any

    options { skipDefaultCheckout() }

    environment{
        /*NOTES:
        lo_var="lowercaseOK" can be called ${LO_VAR}
        black var is interpolated, green is a string
        app_jar='${env.app_name}' - singel quotes dont interpolate variables!!!*/

        app_name="tutorialpedia"
        version="0.0.1"
        app_jar="${env.app_name}-${env.version}"
        serverport=9999
        app_image="${env.app_name}"
        app_container="tut-app"
        db_image="mysql"
        db_container="tut-mysql"

    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Install docker dependency') {
            steps {
                script {
                    def dockerTool = tool name: 'myDocker', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
                    withEnv(["DOCKER=${dockerTool}/bin"]) {
                        //stages
                    }
                }
                //for testing purposes (each sh has its own interpreter - you start afresh each time
                echo 'Careful with variables:'
                //sh '$PWD' // interpolated by Shell :  /var/jenkins_home/workspace/ia_d-06-poll-scm-push-hub_master  Permission denied
                //sh "$PWD" // interpolated in this file  by Groovy:  / Permission denied
                sh 'docker images'
                sh 'printenv'

            }
        }

        stage('Checkout github') {
            steps {
                deleteDir()
                //ALTENATIVE: checkout scm
                git branch: 'master', credentialsId: 'jenkins-webhook-id', url: 'https://github.com/darco2018/tutorial_ranking'

            }
        }


        stage('Build JAR - skip tests (using Maven in Jenkins') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build app image') {
            steps {
                script {

                    myImg = docker.build("ustrd/tutorialpedia:$env.BUILD_NUMBER", "-f ./docker/Dockerfile --build-arg SOMEVAR=dummyvalue . ")
                    /*
                    myImg.inside("--entrypoint=''"){ // turns off Dockerfile entrypoint
                        sh 'ls -al'
                        sh "echo ${app_jar} is app_jar ....." //
                        sh "echo ${APP_JAR} is APP_JAR ....."
                    }
                    */

                }
            }
        }

        stage('Run the app') {
            steps {
                script {
                    try {  // database tez powinna byc z custom Dockerfile: ustrd/mysql
                        sh 'scripts/db-up.sh'
                    } catch (error) {
                    } finally {
                        sh 'scripts/db-down.sh'
                    }
                }
            }
        }


        stage('Connect to dockerhub') {
            steps {

                // This step should not normally be used in your script. Consult the inline help for details.
                // docker.withRegistry(url[, credentialsId]) {…} >> https://index.docker.io/v1/
                // toolName i url moga byc ominiete
                // Docker Pipeline & Docker Commons Plugin
                /* ALTERNATIVE - not in script step)
                withDockerRegistry(credentialsId: 'dockerhub-credentials', url: 'https://index.docker.io/v1/') {
                    // some block
                }
*/               script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {

                    }
                }
            }

            // There are many features of the Pipeline that are not steps. These are often exposed via global variables,
            // which are not supported by the snippet generator.

        }

    }


}


/*

They dont load the variables into Dockerfile or Jenkins environment:
readFile 'scripts/docker-config.sh'
load './scripts/docker-config.groovy'
sh 'source scripts/docker-config.sh'
*/

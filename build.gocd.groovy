package build_multiple_branches

import cd.go.contrib.plugins.configrepo.groovy.dsl.GoCD

GoCD.script {
  branches {
    matching {
      pattern = ~/^refs\/.+/

      githubProvider {
        fullRepoName = "gocd/gocd"
      }

      onMatch { ctx ->
        pipeline("pr-${ctx.branchSanitized}") {
          group = "example-group"

          materials { add(ctx.repo) }

          stages {
            stage('tests') {
              jobs {
                job('units') {
                  tasks { bash { commandString = 'whoami' } }
                }
              }
            }
          }
        }
      }
    }
  }
}

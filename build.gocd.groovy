package build_multiple_branches

import cd.go.contrib.plugins.configrepo.groovy.dsl.GoCD

GoCD.script {
  branches {
    matching {
      pattern = ~/^refs\/pull\/.+/

      from = github {
        fullRepoName = "marques-work/throwaway"
      }

      onMatch { ctx ->
        pipeline("pr-${ctx.branchSanitized}") {
          group = "example-group"

          materials { add(ctx.repo) }

          stages {
            stage('tests') {
              jobs {
                job('units') {
                  tasks { bash { commandString = 'printf "$(git branch)\\n$(git remote show origin)\\n"' } }
                }
              }
            }
          }
        }
      }
    }
  }
}

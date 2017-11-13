Help for MIRA
-----------

```
Usage: mira [-h]
  -h, --help                  Show help message for mira
Commands:
  auth      Authorize, before using MIRA
  project   Manage your projects: show all, filterRest
  issue     Manage your issues: see assigned issues, see your issues,
              filterRest.
  commit    Commit your time: to issues, with favorite lists
  absent    Notify all about your absent today
  start     Start your issues with this command. Automatically change status of
              issue, create branch and etc.
  favorite  Add issues to favorite list
```

List of commands
----------------
### auth
```
Usage: auth [-h] [-k=<apiKey>] [-r=<redmineEndpoint>]
Authorize, before using MIRA
  -h, -?, --help              Help
  -k, --key=<apiKey>          https://redmine.magora.team/my/account
                              Check "API" field
  -r, --redmine=<redmineEndpoint>
                              Domain of your redmine. ex: redmine.magora.com
```

### project
```
Usage: project [-ah] [-f=<query>]
Manage your projects: show all, filterRest
  -a, --all                   Print all available projects for your account
  -f, --filterRest=<query>    Filter projects by id, name and link (short-name)
  -h, -?, --help              Help
```

### issue
```
Usage: issue [-ahp] [-me] [-plus] [-f=<projectName>] [-i=<id>]
             [-fr=<String=String>]...
Manage your issues: see assigned issues, see your issues, filterRest.
  -a, --all                   Show all available info
  -f, --filter=<projectName>  Filter issues by project name
                              

      -fr, --filter-rest=<String=String>
                              Filter issues with specific REST params
                              See all available commands on http://www.redmine.
                                org/projects/redmine/wiki/Rest_Issues
  -h, -?, --help              Help
  -i, -id, --id=<id>          Index of search issue. It`s terminate operation
      -me, --me               Filter issues by assigned to me
  -p, --print                 Print issue data. By default = true
      -plus, --plus           Add selected issue to list of favorites (alias)
```

### commit
```
Usage: commit [-?] [-h=<hours>] -i=<id> [-m=<minutes>]
Commit your time: to issues, with favorite lists
  -?, --help                  Help
  -h, --hours=<hours>         Set hours to time for commit
  -i, -id, --id=<id>          Issue id of task for commit time
  -m, --minutes=<minutes>     Set minutes to time for commit
```

### absent
```
Usage: absent [-?] [-ln=<lastname>] [-r=<reason>] -t=<time>
Notify all about your absent today
  -?, --help                  Help
      -ln, --ln, --lastname=<lastname>
                              The time at which you will be on the job
                                Default:
  -r, --reason=<reason>       Reason, why you can not work
                                Default:
  -t, --time=<time>           The time at which you will be on the job
                                Default:
```

### start
```
Usage: start [-chm] -i=<id> [-p=<postfix>]
Start your issues with this command. Automatically change status of issue,
create branch and etc.
  -c, --create                Create and checkout to issue branch or not
  -h, -?, --help              Help
  -i, -id, --id=<id>          Id of issue, that will be started
                                Default: -2147483648
  -m, -me, --me               Assign issue to you, if it`s already not assigned
  -p, --postfix=<postfix>     Add postfix to your branch name. ex:
                                feature/#12345-postfix
                                Default:
```

### favorite
```
Unmatched argument [-?]
Usage: favorite
Add issues to favorite list
Commands:
  read    Read issues from favorite list
  create  Add issues to favorite list
  update  Update issues from favorite list
  delete  Delete issues from favorite list
```

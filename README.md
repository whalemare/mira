# MIRA
Multi Instrument Redmine Assistant

What is it
----------
Tools for fast commiting time, checkout between branches, manage your redmine issues, project, notify about absent and etc.

Usage
-----

Please, build the project and type in your terminal "help" for see available commands.
This is a very early prototype of the product, but even now something can

Firstly, you need authorize by enter your redmine endpoint and api key of profile, after that you can use commands list bellow

```
Hello, Anton
mira >> -h
Usage: mira [-h]
  -h, --help                  Show help message for mira
Commands:
  auth     Authorize, before using MIRA
  project  Manage your projects: show all, filter
  issue    Manage your issues: see assigned issues, see your issues, filter.
  commit   Commit your time: to issues, with favorite lists
  absent   Notify all about your absent today
  start    Start your issues with this command. Automatically change status of
             issue, create branch and etc.
```

### Use cases

1. View issues assigned to me with all available info
    ```
    mira issue --me -a
    ```

    ```
    Show: 25, Found: 48
    ╔═══════╤═══════════════════════════════╤═══════════════════╤══════════════╤═════════════╤═══════╤═══════════╤════════╗
    ║ id    │ subject                       │ author            │ assigned     │ status      │ spent │ estimated │ closed ║
    ╠═══════╪═══════════════════════════════╪═══════════════════╪══════════════╪═════════════╪═══════╪═══════════╪════════╣
    ║ 80390 │ [Android] SOME wonderful task │ Vlasov Anton      │ Vlasov Anton │ In progress │ ?     │ 6.0       │ ?      ║
    ╟───────┼───────────────────────────────┼───────────────────┼──────────────┼─────────────┼───────┼───────────┼────────╢
    ```
2. View issues assigned to me in scope of project with name "kitwts"
    ```
    issue --me -f kitwts
    ```

    ```
    Show: 3, Found: 3
    ... (this will be data output)
    ```

3. Start issue with id = 123: assign to you, change status to "In Progress" and create-checkout branch for this task with name "feature/#123" or "bug/#123" if this is bug.
    ```
    start -i 123
    ```

4. Same as 3, but branch name has postfix. Final branch name: "task/#123-my-postfix-for-task"
    ```
    start -i 123 -p my-postfix-for-task
    ```

5. Commit 3 hours and 59 minutes to issue with id = 123
    ```
    commit -i 123 -h 3 -m 59
    ```

6. Send email to "absent alias email" of your organization, with message:
    that you be available after 13:30, by reason that you has exam in university,

### Help commands

#### Project
```
project -h
Usage: project [-ah] [-f=<query>]
Manage your projects: show all, filterRest
  -a, --all                   Print all available projects for your account
  -f, --filterRest=<query>    Filter projects by id, name and link (short-name)
  -h, -?, --help              Help
```

#### Issue

```
Usage: issue [-ahp] [-me] [-f=<projectName>] [-i=<id>] [-fr=<String=String>]...
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
```

#### Commit
```
Usage: commit [-?] [-h=<hours>] -i=<id> [-m=<minutes>]
Commit your time: to issues, with favorite lists
  -?, --help                  Help
  -h, --hours=<hours>         Set hours to time for commit
  -i, -id, --id=<id>          Issue id of task for commit time
  -m, --minutes=<minutes>     Set minutes to time for commit
```

#### Absent
```
Usage: absent [-ch] [-r=<reason>] [-s=<sources>] -t=<time>
Notify all about your absent today
  -c, --clear                 Clear you current config for absent: email from,
                                email to, password
  -h, -?, --help              Help
  -r, --reason=<reason>       Reason, why you can not work
                                Default: учёба
  -s, --sources=<sources>     How can members contact with you?
                                Default: skype, почта, телефон
  -t, --time=<time>           The time at which you will be on the job
                                Default:
```
#### Start

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
License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
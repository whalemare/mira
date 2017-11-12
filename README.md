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
    ```
    absent -t 13:00
    ```

### Help commands

You can see list of all available commands [here](help.md)

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
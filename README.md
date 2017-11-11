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
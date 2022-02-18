# Reactor

Reactor library for Java

## TODO

- write tests, including thread safety tests
- SynchronousReactor: follow DRY regarding the locking and unlocking of the observers map, maybe a generic component that serves this purpose is appropriate
- Create factory with thread pool, thread pools are not necessarily thread safe so comment that factory cannot expose instances and check that locking in reactor impl is sufficient for thread safety, async fatory per concurrency policy + bridge pattern - injectiojn of executor - 2 options, flexibility
- Review access control and rearrange accordingly

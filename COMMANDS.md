# Commands

some of the handful commands which will be needful during project

## Gradle commands

to build:

```powershell
.\gradlew clean build
````

to run spring boot application:

```powershell
.\gradlew bootRun
```

to inspect dependencies:

```powershell
.\gradlew dependencies
```

to inspect a specific dependency (useful for conflicts like Jackson):

```powershell
.\gradlew dependencyInsight --dependency jackson
```

to show all deprecation warnings (Gradle 9 readiness):

```powershell
.\gradlew build --warning-mode all
```

to stop all Gradle daemons:

```powershell
.\gradlew --stop
```

to refresh dependencies (fixes cache issues):

```powershell
.\gradlew build --refresh-dependencies
```

to package executable Spring Boot JAR:

```powershell
.\gradlew bootJar
```

to run the packaged JAR:

```powershell
java -jar build/libs/*.jar
```

to check Gradle version used by wrapper:

```powershell
.\gradlew -version
```

## Git Commands

to check status:

```git
git status
```

to add file:

```git
git add <filename>
```

to add all files:

```git
git add *
```

to commit:

```git
git commit -m "commit message"
```

to push changes:

```git
git push
```

to view commit history (compact):

```git
git log --oneline --graph --decorate
```

to see unstaged changes:

```git
git diff
```

to see staged changes only:

```git
git diff --staged
```

to undo last commit but keep changes:

```git
git reset --soft HEAD~1
```

to discard all local changes (⚠ destructive):

```git
git checkout -- .
```

to create a new branch:

```git
git checkout -b feature/new-feature
```

to switch branches:

```git
git checkout main
```

## Environment & Utility Commands

to check Java version:

```powershell
java -version
```

to check JAVA_HOME:

```powershell
echo $Env:JAVA_HOME
```

to check GRADLE_HOME:

```powershell
echo $Env:GRADLE_HOME
```

to make new file / directory:

```powershell
New-Item filename.extension
```

```powershell
New-Item textfile.txt
```

to remove a file / directory:

```powershell
Remove-Item filename.extension
```

```powershell
Remove-Item textfile.txt
```

to remove build directory:

```powershell
Remove-Item -Recurse -Force build
```

to clear Gradle cache (last resort):

```powershell
Remove-Item -Recurse -Force ~/.gradle/caches
```

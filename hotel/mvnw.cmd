@echo off
:: ----------------------------------------------------------------------------
:: Maven Wrapper (Windows)
:: ----------------------------------------------------------------------------
setlocal
set MAVEN_PROJECTBASEDIR=%~dp0

if defined JAVA_HOME (
    set _JAVA=%JAVA_HOME%\bin\java
) else (
    where java >nul 2>nul
    if errorlevel 1 (
        echo Error: JAVA_HOME is not set and 'java' could not be found in PATH.
        exit /b 1
    )
    for /f "delims=" %%i in ('where java') do set _JAVA=%%i
)



endlocaln"%_JAVA%" -jar "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" %*
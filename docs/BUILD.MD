To build this mod addon, you must have [Git](https://git-scm.com/) installed on your pc. After installing it, open the Git Bash command line in your preferable folder and clone the repository using:

```
git clone https://github.com/DavixDevelop/terracommondatasets.git
```

After cloning the repository, you have two options. You can build the mod addon or you can open the project in an IDE like IntelliJ IDEA for development purposes.

## Build the mod addon

Open the cloned repository in the command line, and if you are using Windows, simply execute
```
gradlew.bat build
```

If you are using Linux or macOS you may need to execute
```bash
sudo chmod +x gradlew
```
...with the following afterward
```
./gradlew build
```

After a successful build, copy the jar from `build/libs` to your mods folder and make sure you have [`TerraPlusPlus`](https://github.com/BuildTheEarth/terraplusplus/tree/master/docs) installed.

## Development purposes

Open the cloned repository in the command line. Next, execute `gradlew setupDecompWorkspace` if you are on Windows or `./gradlew setupDecompWorkspace` if you are on Linux/macOS.

Next, if you are using Eclipse, execute `gradlew eclipse` (Windows) or `./gradlew eclipse` (Linux/macOS) then open the project in Eclipse.

If you are using IntelliJ IDEA, first import the project by selecting the build.gradle file when importing. After the project finishes importing, close IntelliJ IDEA and execute `gradlew genIntellijRuns` (Windows) or `./gradlew genIntellijRuns` (Linux/macOS). Then you can open IntelliJ IDEA again and open the project.
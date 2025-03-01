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

If you are using Eclipse, execute `gradlew eclipse` (Windows) or `./gradlew eclipse` (Linux/macOS) then open the project in Eclipse.

If you are using IntelliJ IDEA, first import the project by selecting the build.gradle file when importing. After the project finishes importing, close IntelliJ IDEA and execute `gradlew genIntellijRuns` (Windows) or `./gradlew genIntellijRuns` (Linux/macOS). Then you can open IntelliJ IDEA again and open the project.

>**IMPORTANT (Credit to SmylerMC):** Due to a bug with MIXIN, if a mod contains `mixin`, `coremod` and a `normal forge mod` it, when run in a dev environment adds CC as reparseable core mode, which cause FML to not load the CC forge mod.<br>
> To trick `MixinPlatformAgentFMLLegacy#isIgnoredReparseable()` to load the forge mod, you must add a symlink to the CC jar (inside the .gradle cache folder) in your mods folder (run/mods).
> Or you can add the `--mods` option to the CLI arguments (in the `runClient` configuration) to point to said CC jar, ex:<br>
> `--mods=../../../../.gradle/caches/forge_gradle/deobf_dependencies/com/github/OpenCubicChunks/CubicChunks/1b8a0662a5_mapped_stable_39-1.12/CubicChunks-1b8a0662a5_mapped_stable_39-1.12.jar`
> (Adjust the `../` amount to point to the jar)
> 
> The bug is supposed to be fixed in [Mixin: Issue 201](https://github.com/SpongePowered/Mixin/issues/207) but still happens as noted in [Mixin: Issue 319](https://github.com/SpongePowered/Mixin/issues/319).

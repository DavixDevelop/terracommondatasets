- [0.3](https://github.com/DavixDevelop/terracommondatasets/releases/tag/0.3)
  - Compatibility bump to TerraPlusPlus 1.1.0.627+ (Because of update to PorkLib)
  - Changed versioning to `0.3.[GitHub commit count]`

    `Release note:`
    > Use this version for post TerraPlusPlus 1.1.0.627 releases
- [0.2](https://github.com/DavixDevelop/terracommondatasets/releases/tag/0.2)
  - Initial release of the common library mod
  - Fixed issue with a dependency of ForgeGradle being compiled against J… DK 11 (and not JDK 8), by switching to the https://maven.daporkchop.net repository
  - Fixed Koppen classification for Prague (This update fixes the desert being generated in Prague with the  
    terraimprovedbiomes addon. It's now replaced with Dark Forest)
  - Fixed OutOfMemoryError for built in datasets
  - Removed jcenter

    `Release note:`
    > Use this version for pre TerraPlusPlus 1.1.0.627 releases
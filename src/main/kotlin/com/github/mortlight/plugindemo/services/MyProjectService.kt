package com.github.mortlight.plugindemo.services

import com.github.mortlight.plugindemo.MyBundle
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import se.kth.spork.spoon.Spoon3dmMerge.merge
import java.nio.file.Path
import java.nio.file.Paths

@Service(Service.Level.PROJECT)
class MyProjectService(project: Project) {

    init {
        thisLogger().info(MyBundle.message("projectService", project.name))
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    fun getRandomNumber() = (1..100).random()

    var RESOURCES_BASE_DIR = Paths.get("src/test/resources")
    var CONFLICT_DIRPATH: Path = RESOURCES_BASE_DIR.resolve("conflict")

//    fun getMix() = merge('sources.base', sources.left, sources.right);
}

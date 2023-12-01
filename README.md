# Extended-Aggregation-Toolkit
An extended plugin for Spork tookit


## Plugin Development Process
- [x] Create a plugin templete via IDEA.
- [x] Configure settings `plugin.xml` and dependencies `build.gradle.kts`.
- [x] Design the UI and implement the logic.
- [x] Testing and debugging.
- [x] Packaging and release on [Github](https://github.com/Stardust-lf/Extended-Aggregation-Toolkit).


<!-- Plugin description -->
This is a merge tool for Java files. It can merge two Java files with the same name and different content into one file. The merged file can be saved to local/cloud storage. The merged file can be previewed in the form of an abstract syntax tree.
<!-- Plugin description end -->

## Usage

- Choose files to be merged
  
  <kbd>Base.java</kbd> > <kbd>Left.java</kbd> > <kbd>Right.java</kbd> > <kbd>Submit</kbd> >
  <kbd>Merge</kbd>
  
- Generate the merged file and save to local/cloud storage:

  <kbd>Choose filepath</kbd> > <kbd>Enter filename</kbd> > <kbd>️submit</kbd> > <kbd>apply</kbd>

- Show the Abstract syntax tree of the merged file:

  <kbd>preview</kbd>
---
Plugin based on [Spork][spork].

[spork]: https://github.com/ASSERT-KTH/spork
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation

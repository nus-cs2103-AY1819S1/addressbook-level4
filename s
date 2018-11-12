 .gitattributes                                     |    1 [32m+[m
 .gitignore                                         |   20 [32m+[m
 .travis.yml                                        |   29 [32m+[m
 AboutUs.html                                       |   79 [31m-[m
 ContactUs.html                                     |   15 [31m-[m
 DeveloperGuide.html                                | 2327 [31m--------------------[m
 HelpWindow.html                                    |  969 [31m--------[m
 LICENSE                                            |   21 [32m+[m
 README.adoc                                        |   38 [32m+[m
 UserGuide.html                                     |  969 [31m--------[m
 UsingAppVeyor.html                                 |  159 [31m--[m
 UsingCheckstyle.html                               |   94 [31m-[m
 UsingCoveralls.html                                |   99 [31m-[m
 UsingGradle.html                                   |  258 [31m---[m
 UsingNetlify.html                                  |  126 [31m--[m
 UsingTravis.html                                   |  283 [31m---[m
 _config.yml                                        |    1 [32m+[m
 appveyor.yml                                       |   18 [32m+[m
 build.gradle                                       |  247 [32m+++[m
 config/checkstyle/checkstyle.xml                   |  423 [32m++++[m
 config/checkstyle/suppressions.xml                 |    9 [32m+[m
 config/travis/check-eof-newline.sh                 |   18 [32m+[m
 config/travis/check-line-endings.sh                |   19 [32m+[m
 config/travis/check-trailing-whitespace.sh         |   26 [32m+[m
 config/travis/deploy_github_pages.sh               |   42 [32m+[m
 config/travis/run-checks.sh                        |   11 [32m+[m
 copyright.txt                                      |    9 [32m+[m
 docs/AboutUs.adoc                                  |   48 [32m+[m
 docs/ContactUs.adoc                                |    7 [32m+[m
 docs/DeveloperGuide.adoc                           |  982 [32m+++++++++[m
 DummySearchPage.html => docs/DummySearchPage.html  |    0
 docs/HelpWindow.adoc                               |    3 [32m+[m
 docs/UserGuide.adoc                                |  452 [32m++++[m
 docs/UsingAppVeyor.adoc                            |   89 [32m+[m
 docs/UsingCheckstyle.adoc                          |   42 [32m+[m
 docs/UsingCoveralls.adoc                           |   54 [32m+[m
 docs/UsingGradle.adoc                              |  113 [32m+[m
 docs/UsingNetlify.adoc                             |   50 [32m+[m
 docs/UsingTravis.adoc                              |  132 [32m++[m
 docs/diagrams/ArchitectureDiagram.pptx             |  Bin [31m0[m -> [32m57574[m bytes
 docs/diagrams/HighLevelSequenceDiagrams.pptx       |  Bin [31m0[m -> [32m71479[m bytes
 docs/diagrams/LogicComponentClassDiagram.pptx      |  Bin [31m0[m -> [32m45584[m bytes
 docs/diagrams/LogicComponentSequenceDiagram.pptx   |  Bin [31m0[m -> [32m44254[m bytes
 .../ModelComponentClassBetterOopDiagram.pptx       |  Bin [31m0[m -> [32m42405[m bytes
 docs/diagrams/ModelComponentClassDiagram.pptx      |  Bin [31m0[m -> [32m41337[m bytes
 docs/diagrams/StorageComponentClassDiagram.pptx    |  Bin [31m0[m -> [32m52461[m bytes
 docs/diagrams/UiComponentClassDiagram.pptx         |  Bin [31m0[m -> [32m40744[m bytes
 docs/diagrams/UndoRedoActivityDiagram.pptx         |  Bin [31m0[m -> [32m36901[m bytes
 .../UndoRedoExecuteUndoStateListDiagram.pptx       |  Bin [31m0[m -> [32m39950[m bytes
 .../UndoRedoNewCommand1StateListDiagram.pptx       |  Bin [31m0[m -> [32m38706[m bytes
 .../UndoRedoNewCommand2StateListDiagram.pptx       |  Bin [31m0[m -> [32m39535[m bytes
 .../UndoRedoNewCommand3StateListDiagram.pptx       |  Bin [31m0[m -> [32m39385[m bytes
 .../UndoRedoNewCommand4StateListDiagram.pptx       |  Bin [31m0[m -> [32m40255[m bytes
 docs/diagrams/UndoRedoSequenceDiagram.pptx         |  Bin [31m0[m -> [32m44036[m bytes
 .../diagrams/UndoRedoStartingStateListDiagram.pptx |  Bin [31m0[m -> [32m32425[m bytes
 {images => docs/images}/Architecture.png           |  Bin
 {images => docs/images}/DeletePersonSdForLogic.png |  Bin
 {images => docs/images}/LogicClassDiagram.png      |  Bin
 .../images}/ModelClassBetterOopDiagram.png         |  Bin
 {images => docs/images}/ModelClassDiagram.png      |  Bin
 {images => docs/images}/SDforDeletePerson.png      |  Bin
 .../images}/SDforDeletePersonEventHandling.png     |  Bin
 {images => docs/images}/SeEduLogo.png              |  Bin
 {images => docs/images}/StorageClassDiagram.png    |  Bin
 {images => docs/images}/Ui.png                     |  Bin
 {images => docs/images}/UiClassDiagram.png         |  Bin
 .../images}/UndoRedoActivityDiagram.png            |  Bin
 .../UndoRedoExecuteUndoStateListDiagram.png        |  Bin
 .../UndoRedoNewCommand1StateListDiagram.png        |  Bin
 .../UndoRedoNewCommand2StateListDiagram.png        |  Bin
 .../UndoRedoNewCommand3StateListDiagram.png        |  Bin
 .../UndoRedoNewCommand4StateListDiagram.png        |  Bin
 .../images}/UndoRedoSequenceDiagram.png            |  Bin
 .../images}/UndoRedoStartingStateListDiagram.png   |  Bin
 {images => docs/images}/YangYafei1998.png          |  Bin
 {images => docs/images}/appveyor/add-project-1.png |  Bin
 {images => docs/images}/appveyor/add-project-2.png |  Bin
 {images => docs/images}/appveyor/add-project-3.png |  Bin
 {images => docs/images}/appveyor/ci-log.png        |  Bin
 {images => docs/images}/appveyor/ci-pending.png    |  Bin
 {images => docs/images}/appveyor/login.png         |  Bin
 .../images}/appveyor/project-settings-1.png        |  Bin
 .../images}/appveyor/project-settings-2.png        |  Bin
 .../images}/appveyor/project-settings-3.png        |  Bin
 {images => docs/images}/build_pending.png          |  Bin
 .../images}/checkstyle-idea-configuration.png      |  Bin
 .../images}/checkstyle-idea-scan-scope.png         |  Bin
 {images => docs/images}/chrome_save_as_pdf.png     |  Bin
 {images => docs/images}/coveralls/badge_repo.png   |  Bin
 .../images}/coveralls/coverage_asciidoc_code.png   |  Bin
 .../images}/coveralls/coverage_report.png          |  Bin
 .../images}/coveralls/disable_comments.png         |  Bin
 .../images}/coveralls/flick_repository_switch.png  |  Bin
 .../images}/coveralls/github_settings.png          |  Bin
 {images => docs/images}/coveralls/sync_repos.png   |  Bin
 {images => docs/images}/damithc.jpg                |  Bin
 {images => docs/images}/elroyhaw.png               |  Bin
 .../images}/flick_repository_switch.png            |  Bin
 {images => docs/images}/florafong97.png            |  Bin
 {images => docs/images}/generate_token.png         |  Bin
 .../images}/getting-started-ui-result-after.png    |  Bin
 .../images}/getting-started-ui-result-before.png   |  Bin
 .../images}/getting-started-ui-status-after.png    |  Bin
 .../images}/getting-started-ui-status-before.png   |  Bin
 .../images}/getting-started-ui-tag-after.png       |  Bin
 .../images}/getting-started-ui-tag-before.png      |  Bin
 {images => docs/images}/giamjuxian.png             |  Bin
 {images => docs/images}/github_repo_settings.png   |  Bin
 {images => docs/images}/grant_access.png           |  Bin
 {images => docs/images}/lejolly.jpg                |  Bin
 {images => docs/images}/m133225.jpg                |  Bin
 .../images}/netlify/change_site_name.png           |  Bin
 .../images}/netlify/grant_or_request_access.png    |  Bin
 .../images}/netlify/netlify_details.png            |  Bin
 {images => docs/images}/netlify/temp_site_name.png |  Bin
 {images => docs/images}/request_access.png         |  Bin
 {images => docs/images}/review_and_add.png         |  Bin
 {images => docs/images}/signing_in.png             |  Bin
 {images => docs/images}/travis_add_token.png       |  Bin
 {images => docs/images}/travis_build.png           |  Bin
 {images => docs/images}/yijinl.jpg                 |  Bin
 {images => docs/images}/yl_coder.jpg               |  Bin
 docs/index.adoc                                    |    2 [32m+[m
 {stylesheets => docs/stylesheets}/asciidoctor.css  |    0
 {stylesheets => docs/stylesheets}/gh-pages.css     |    0
 docs/team/johndoe.adoc                             |   72 [32m+[m
 docs/templates/LICENSE                             |   24 [32m+[m
 docs/templates/_footer.html.slim                   |   12 [32m+[m
 docs/templates/_footnotes.html.slim                |    7 [32m+[m
 docs/templates/_header.html.slim                   |   76 [32m+[m
 docs/templates/_toc.html.slim                      |    5 [32m+[m
 docs/templates/document.html.slim                  |   29 [32m+[m
 docs/templates/helpers.rb                          |  300 [32m+++[m
 gradle.properties                                  |    2 [32m+[m
 gradle/wrapper/gradle-wrapper.jar                  |  Bin [31m0[m -> [32m54329[m bytes
 gradle/wrapper/gradle-wrapper.properties           |    5 [32m+[m
 gradlew                                            |  172 [32m++[m
 gradlew.bat                                        |   84 [32m+[m
 index.html                                         |   83 [31m-[m
 src/main/java/seedu/address/AppParameters.java     |   64 [32m+[m
 src/main/java/seedu/address/MainApp.java           |  208 [32m++[m
 .../address/commons/core/ComponentManager.java     |   28 [32m+[m
 .../java/seedu/address/commons/core/Config.java    |   74 [32m+[m
 .../seedu/address/commons/core/EventsCenter.java   |   45 [32m+[m
 .../seedu/address/commons/core/GuiSettings.java    |   73 [32m+[m
 .../seedu/address/commons/core/LogsCenter.java     |  128 [32m++[m
 .../java/seedu/address/commons/core/Messages.java  |   13 [32m+[m
 .../java/seedu/address/commons/core/Version.java   |  113 [32m+[m
 .../seedu/address/commons/core/index/Index.java    |   54 [32m+[m
 .../seedu/address/commons/events/BaseEvent.java    |   16 [32m+[m
 .../events/model/AddressBookChangedEvent.java      |   19 [32m+[m
 .../events/storage/DataSavingExceptionEvent.java   |   21 [32m+[m
 .../commons/events/ui/ExitAppRequestEvent.java     |   14 [32m+[m
 .../commons/events/ui/JumpToListRequestEvent.java  |   22 [32m+[m
 .../commons/events/ui/NewResultAvailableEvent.java |   21 [32m+[m
 .../ui/PersonPanelSelectionChangedEvent.java       |   26 [32m+[m
 .../commons/events/ui/ShowHelpRequestEvent.java    |   15 [32m+[m
 .../exceptions/DataConversionException.java        |   11 [32m+[m
 .../commons/exceptions/IllegalValueException.java  |   21 [32m+[m
 .../java/seedu/address/commons/util/AppUtil.java   |   39 [32m+[m
 .../seedu/address/commons/util/CollectionUtil.java |   35 [32m+[m
 .../seedu/address/commons/util/ConfigUtil.java     |   23 [32m+[m
 .../java/seedu/address/commons/util/FileUtil.java  |   83 [32m+[m
 .../java/seedu/address/commons/util/JsonUtil.java  |  143 [32m++[m
 .../seedu/address/commons/util/StringUtil.java     |   68 [32m+[m
 .../java/seedu/address/commons/util/XmlUtil.java   |   71 [32m+[m
 .../java/seedu/address/logic/CommandHistory.java   |   58 [32m+[m
 .../seedu/address/logic/ListElementPointer.java    |  111 [32m+[m
 src/main/java/seedu/address/logic/Logic.java       |   27 [32m+[m
 .../java/seedu/address/logic/LogicManager.java     |   52 [32m+[m
 .../logic/commands/AddAppointmentCommand.java      |   91 [32m+[m
 .../seedu/address/logic/commands/AddCommand.java   |   69 [32m+[m
 .../seedu/address/logic/commands/ClearCommand.java |   25 [32m+[m
 .../java/seedu/address/logic/commands/Command.java |   22 [32m+[m
 .../address/logic/commands/CommandResult.java      |   16 [32m+[m
 .../address/logic/commands/DeleteCommand.java      |   66 [32m+[m
 .../logic/commands/DeleteDoctorCommand.java        |   31 [32m+[m
 .../logic/commands/DeletePatientCommand.java       |   31 [32m+[m
 .../seedu/address/logic/commands/EditCommand.java  |  230 [32m++[m
 .../seedu/address/logic/commands/ExitCommand.java  |   23 [32m+[m
 .../seedu/address/logic/commands/FindCommand.java  |   43 [32m+[m
 .../seedu/address/logic/commands/HelpCommand.java  |   25 [32m+[m
 .../address/logic/commands/HistoryCommand.java     |   33 [32m+[m
 .../seedu/address/logic/commands/ListCommand.java  |   25 [32m+[m
 .../seedu/address/logic/commands/RedoCommand.java  |   31 [32m+[m
 .../logic/commands/RegisterDoctorCommand.java      |   65 [32m+[m
 .../logic/commands/RegisterPatientCommand.java     |   65 [32m+[m
 .../address/logic/commands/RemarkCommand.java      |   87 [32m+[m
 .../address/logic/commands/SelectCommand.java      |   57 [32m+[m
 .../seedu/address/logic/commands/UndoCommand.java  |   31 [32m+[m
 .../commands/exceptions/CommandException.java      |   17 [32m+[m
 .../logic/parser/AddAppointmentCommandParser.java  |   46 [32m+[m
 .../address/logic/parser/AddCommandParser.java     |   62 [32m+[m
 .../address/logic/parser/AddressBookParser.java    |  111 [32m+[m
 .../address/logic/parser/ArgumentMultimap.java     |   60 [32m+[m
 .../address/logic/parser/ArgumentTokenizer.java    |  148 [32m++[m
 .../java/seedu/address/logic/parser/CliSyntax.java |   19 [32m+[m
 .../logic/parser/DeleteDoctorCommandParser.java    |   45 [32m+[m
 .../logic/parser/DeletePatientCommandParser.java   |   45 [32m+[m
 .../address/logic/parser/EditCommandParser.java    |   82 [32m+[m
 .../address/logic/parser/FindCommandParser.java    |   33 [32m+[m
 .../java/seedu/address/logic/parser/Parser.java    |   16 [32m+[m
 .../seedu/address/logic/parser/ParserUtil.java     |  146 [32m++[m
 .../java/seedu/address/logic/parser/Prefix.java    |   39 [32m+[m
 .../logic/parser/RegisterDoctorCommandParser.java  |   65 [32m+[m
 .../logic/parser/RegisterPatientCommandParser.java |   65 [32m+[m
 .../address/logic/parser/RemarkCommandParser.java  |   39 [32m+[m
 .../address/logic/parser/SelectCommandParser.java  |   28 [32m+[m
 .../logic/parser/exceptions/ParseException.java    |   17 [32m+[m
 src/main/java/seedu/address/model/AddressBook.java |  131 [32m++[m
 src/main/java/seedu/address/model/Model.java       |   94 [32m+[m
 .../java/seedu/address/model/ModelManager.java     |  162 [32m++[m
 .../seedu/address/model/ReadOnlyAddressBook.java   |   17 [32m+[m
 src/main/java/seedu/address/model/UserPrefs.java   |   69 [32m+[m
 .../seedu/address/model/VersionedAddressBook.java  |  109 [32m+[m
 .../address/model/appointment/Appointment.java     |   44 [32m+[m
 .../address/model/appointment/Prescription.java    |   38 [32m+[m
 .../seedu/address/model/appointment/Status.java    |    8 [32m+[m
 .../java/seedu/address/model/doctor/Doctor.java    |   59 [32m+[m
 .../address/model/patient/MedicalHistory.java      |   33 [32m+[m
 .../java/seedu/address/model/patient/Patient.java  |   83 [32m+[m
 .../java/seedu/address/model/person/Address.java   |   59 [32m+[m
 .../java/seedu/address/model/person/Email.java     |   67 [32m+[m
 src/main/java/seedu/address/model/person/Name.java |   59 [32m+[m
 .../person/NameContainsKeywordsPredicate.java      |   31 [32m+[m
 .../java/seedu/address/model/person/Person.java    |  129 [32m++[m
 .../java/seedu/address/model/person/Phone.java     |   53 [32m+[m
 .../java/seedu/address/model/person/Remark.java    |   37 [32m+[m
 .../address/model/person/UniquePersonList.java     |  135 [32m++[m
 .../exceptions/DuplicatePersonException.java       |   11 [32m+[m
 .../person/exceptions/PersonNotFoundException.java |    6 [32m+[m
 src/main/java/seedu/address/model/tag/Tag.java     |   54 [32m+[m
 .../seedu/address/model/util/SampleDataUtil.java   |   65 [32m+[m
 .../seedu/address/storage/AddressBookStorage.java  |   45 [32m+[m
 .../address/storage/JsonUserPrefsStorage.java      |   46 [32m+[m
 src/main/java/seedu/address/storage/Storage.java   |   39 [32m+[m
 .../java/seedu/address/storage/StorageManager.java |   93 [32m+[m
 .../seedu/address/storage/UserPrefsStorage.java    |   35 [32m+[m
 .../seedu/address/storage/XmlAdaptedPerson.java    |  148 [32m++[m
 .../java/seedu/address/storage/XmlAdaptedTag.java  |   62 [32m+[m
 .../address/storage/XmlAddressBookStorage.java     |   80 [32m+[m
 .../java/seedu/address/storage/XmlFileStorage.java |   39 [32m+[m
 .../storage/XmlSerializableAddressBook.java        |   71 [32m+[m
 src/main/java/seedu/address/ui/BrowserPanel.java   |   72 [32m+[m
 src/main/java/seedu/address/ui/CommandBox.java     |  174 [32m++[m
 src/main/java/seedu/address/ui/HelpWindow.java     |   78 [32m+[m
 src/main/java/seedu/address/ui/MainWindow.java     |  203 [32m++[m
 src/main/java/seedu/address/ui/PersonCard.java     |   85 [32m+[m
 .../java/seedu/address/ui/PersonListPanel.java     |   83 [32m+[m
 src/main/java/seedu/address/ui/ResultDisplay.java  |   41 [32m+[m
 .../java/seedu/address/ui/StatusBarFooter.java     |   83 [32m+[m
 src/main/java/seedu/address/ui/Ui.java             |   16 [32m+[m
 src/main/java/seedu/address/ui/UiManager.java      |  120 [32m+[m
 src/main/java/seedu/address/ui/UiPart.java         |  106 [32m+[m
 src/main/resources/images/address_book_32.png      |  Bin [31m0[m -> [32m4214[m bytes
 src/main/resources/images/calendar.png             |  Bin [31m0[m -> [32m1215[m bytes
 src/main/resources/images/clock.png                |  Bin [31m0[m -> [32m13751[m bytes
 src/main/resources/images/fail.png                 |  Bin [31m0[m -> [32m20783[m bytes
 src/main/resources/images/help_icon.png            |  Bin [31m0[m -> [32m31256[m bytes
 src/main/resources/images/info_icon.png            |  Bin [31m0[m -> [32m5424[m bytes
 src/main/resources/view/BrowserPanel.fxml          |   64 [32m+[m
 src/main/resources/view/CommandBox.fxml            |    9 [32m+[m
 src/main/resources/view/DarkTheme.css              |  351 [32m+++[m
 src/main/resources/view/Extensions.css             |   20 [32m+[m
 src/main/resources/view/HelpWindow.fxml            |   18 [32m+[m
 src/main/resources/view/MainWindow.fxml            |   68 [32m+[m
 src/main/resources/view/PersonListCard.fxml        |   37 [32m+[m
 src/main/resources/view/PersonListPanel.fxml       |    8 [32m+[m
 src/main/resources/view/ResultDisplay.fxml         |    9 [32m+[m
 src/main/resources/view/StatusBarFooter.fxml       |   14 [32m+[m
 src/main/resources/view/default.html               |    9 [32m+[m
 src/test/data/ConfigUtilTest/EmptyConfig.json      |    3 [32m+[m
 .../data/ConfigUtilTest/ExtraValuesConfig.json     |    6 [32m+[m
 .../data/ConfigUtilTest/NotJsonFormatConfig.json   |    1 [32m+[m
 src/test/data/ConfigUtilTest/TypicalConfig.json    |    5 [32m+[m
 .../JsonUserPrefsStorageTest/EmptyUserPrefs.json   |    3 [32m+[m
 .../ExtraValuesUserPref.json                       |   13 [32m+[m
 .../NotJsonFormatUserPrefs.json                    |    1 [32m+[m
 .../JsonUserPrefsStorageTest/TypicalUserPref.json  |   11 [32m+[m
 .../NotXmlFormatAddressBook.xml                    |    1 [32m+[m
 .../invalidAndValidPersonAddressBook.xml           |   19 [32m+[m
 .../invalidPersonAddressBook.xml                   |   10 [32m+[m
 .../duplicatePersonAddressBook.xml                 |   22 [32m+[m
 .../invalidPersonAddressBook.xml                   |   10 [32m+[m
 .../typicalPersonsHealthBook.xml                  |   57 [32m+[m
 src/test/data/XmlUtilTest/empty.xml                |    0
 src/test/data/XmlUtilTest/invalidPersonField.xml   |   10 [32m+[m
 src/test/data/XmlUtilTest/missingPersonField.xml   |    9 [32m+[m
 src/test/data/XmlUtilTest/tempAddressBook.xml      |   12 [32m+[m
 src/test/data/XmlUtilTest/validAddressBook.xml     |   66 [32m+[m
 src/test/data/XmlUtilTest/validPerson.xml          |    9 [32m+[m
 src/test/java/guitests/GuiRobot.java               |  119 [32m+[m
 .../guitests/guihandles/AlertDialogHandle.java     |   32 [32m+[m
 .../guitests/guihandles/BrowserPanelHandle.java    |   64 [32m+[m
 .../java/guitests/guihandles/CommandBoxHandle.java |   42 [32m+[m
 .../java/guitests/guihandles/HelpWindowHandle.java |   34 [32m+[m
 .../java/guitests/guihandles/MainMenuHandle.java   |   39 [32m+[m
 .../java/guitests/guihandles/MainWindowHandle.java |   51 [32m+[m
 src/test/java/guitests/guihandles/NodeHandle.java  |   44 [32m+[m
 .../java/guitests/guihandles/PersonCardHandle.java |   95 [32m+[m
 .../guitests/guihandles/PersonListPanelHandle.java |  159 [32m++[m
 .../guitests/guihandles/ResultDisplayHandle.java   |   22 [32m+[m
 src/test/java/guitests/guihandles/StageHandle.java |   63 [32m+[m
 .../guitests/guihandles/StatusBarFooterHandle.java |   72 [32m+[m
 src/test/java/guitests/guihandles/WebViewUtil.java |   31 [32m+[m
 .../exceptions/NodeNotFoundException.java          |    9 [32m+[m
 .../exceptions/StageNotFoundException.java         |    9 [32m+[m
 src/test/java/seedu/address/AppParametersTest.java |   58 [32m+[m
 src/test/java/seedu/address/TestApp.java           |  120 [32m+[m
 .../seedu/address/commons/core/ConfigTest.java     |   32 [32m+[m
 .../seedu/address/commons/core/VersionTest.java    |  139 [32m++[m
 .../address/commons/core/index/IndexTest.java      |   92 [32m+[m
 .../seedu/address/commons/util/AppUtilTest.java    |   47 [32m+[m
 .../address/commons/util/CollectionUtilTest.java   |  117 [32m+[m
 .../seedu/address/commons/util/ConfigUtilTest.java |  131 [32m++[m
 .../seedu/address/commons/util/FileUtilTest.java   |   24 [32m+[m
 .../seedu/address/commons/util/JsonUtilTest.java   |   45 [32m+[m
 .../seedu/address/commons/util/StringUtilTest.java |  159 [32m++[m
 .../seedu/address/commons/util/XmlUtilTest.java    |  148 [32m++[m
 .../seedu/address/logic/CommandHistoryTest.java    |   79 [32m+[m
 .../address/logic/ListElementPointerTest.java      |  175 [32m++[m
 .../java/seedu/address/logic/LogicManagerTest.java |  116 [32m+[m
 .../logic/commands/AddCommandIntegrationTest.java  |   49 [32m+[m
 .../address/logic/commands/AddCommandTest.java     |  204 [32m++[m
 .../address/logic/commands/ClearCommandTest.java   |   37 [32m+[m
 .../address/logic/commands/CommandTestUtil.java    |  144 [32m++[m
 .../logic/commands/DeletePatientCommandTest.java   |   16 [32m+[m
 .../address/logic/commands/EditCommandTest.java    |  250 [32m+++[m
 .../logic/commands/EditPersonDescriptorTest.java   |   58 [32m+[m
 .../address/logic/commands/ExitCommandTest.java    |   30 [32m+[m
 .../address/logic/commands/FindCommandTest.java    |   85 [32m+[m
 .../address/logic/commands/HelpCommandTest.java    |   30 [32m+[m
 .../address/logic/commands/HistoryCommandTest.java |   35 [32m+[m
 .../address/logic/commands/ListCommandTest.java    |   41 [32m+[m
 .../address/logic/commands/RedoCommandTest.java    |   49 [32m+[m
 .../address/logic/commands/RemarkCommandTest.java  |  206 [32m++[m
 .../address/logic/commands/SelectCommandTest.java  |  118 [32m+[m
 .../address/logic/commands/UndoCommandTest.java    |   45 [32m+[m
 .../address/logic/parser/AddCommandParserTest.java |  141 [32m++[m
 .../logic/parser/AddressBookParserTest.java        |  147 [32m++[m
 .../logic/parser/ArgumentTokenizerTest.java        |  150 [32m++[m
 .../logic/parser/CommandParserTestUtil.java        |   38 [32m+[m
 .../logic/parser/EditCommandParserTest.java        |  211 [32m++[m
 .../logic/parser/FindCommandParserTest.java        |   34 [32m+[m
 .../seedu/address/logic/parser/ParserUtilTest.java |  208 [32m++[m
 .../logic/parser/RemarkCommandParserTest.java      |   62 [32m+[m
 .../logic/parser/SelectCommandParserTest.java      |   29 [32m+[m
 .../java/seedu/address/model/AddressBookTest.java  |  110 [32m+[m
 .../java/seedu/address/model/ModelManagerTest.java |   84 [32m+[m
 .../address/model/VersionedAddressBookTest.java    |  298 [32m+++[m
 .../seedu/address/model/person/AddressTest.java    |   37 [32m+[m
 .../java/seedu/address/model/person/EmailTest.java |   62 [32m+[m
 .../person/NameContainsKeywordsPredicateTest.java  |   75 [32m+[m
 .../java/seedu/address/model/person/NameTest.java  |   41 [32m+[m
 .../seedu/address/model/person/PersonTest.java     |   99 [32m+[m
 .../java/seedu/address/model/person/PhoneTest.java |   41 [32m+[m
 .../seedu/address/model/person/RemarkTest.java     |   31 [32m+[m
 .../address/model/person/UniquePersonListTest.java |  185 [32m++[m
 src/test/java/seedu/address/model/tag/TagTest.java |   26 [32m+[m
 .../address/storage/JsonUserPrefsStorageTest.java  |  134 [32m++[m
 .../seedu/address/storage/StorageManagerTest.java  |  103 [32m+[m
 .../address/storage/XmlAdaptedPersonTest.java      |  115 [32m+[m
 .../address/storage/XmlAddressBookStorageTest.java |  127 [32m++[m
 .../storage/XmlSerializableAddressBookTest.java    |   53 [32m+[m
 .../seedu/address/testutil/AddressBookBuilder.java |   34 [32m+[m
 src/test/java/seedu/address/testutil/Assert.java   |   53 [32m+[m
 .../testutil/EditPersonDescriptorBuilder.java      |   87 [32m+[m
 .../java/seedu/address/testutil/EventsUtil.java    |   26 [32m+[m
 .../java/seedu/address/testutil/PersonBuilder.java |  106 [32m+[m
 .../java/seedu/address/testutil/PersonUtil.java    |   62 [32m+[m
 .../address/testutil/SerializableTestClass.java    |   72 [32m+[m
 src/test/java/seedu/address/testutil/TestUtil.java |   55 [32m+[m
 .../seedu/address/testutil/TypicalIndexes.java     |   12 [32m+[m
 .../seedu/address/testutil/TypicalPersons.java     |   79 [32m+[m
 .../java/seedu/address/ui/BrowserPanelTest.java    |   48 [32m+[m
 src/test/java/seedu/address/ui/CommandBoxTest.java |  157 [32m++[m
 src/test/java/seedu/address/ui/GuiUnitTest.java    |   38 [32m+[m
 src/test/java/seedu/address/ui/HelpWindowTest.java |   69 [32m+[m
 .../java/seedu/address/ui/MainWindowCloseTest.java |   83 [32m+[m
 src/test/java/seedu/address/ui/PersonCardTest.java |   72 [32m+[m
 .../java/seedu/address/ui/PersonListPanelTest.java |  125 [32m++[m
 .../java/seedu/address/ui/ResultDisplayTest.java   |   38 [32m+[m
 .../java/seedu/address/ui/StatusBarFooterTest.java |   77 [32m+[m
 src/test/java/seedu/address/ui/TestFxmlObject.java |   35 [32m+[m
 src/test/java/seedu/address/ui/UiPartTest.java     |  118 [32m+[m
 .../address/ui/testutil/EventsCollectorRule.java   |   86 [32m+[m
 .../seedu/address/ui/testutil/GuiTestAssert.java   |   76 [32m+[m
 .../java/seedu/address/ui/testutil/StageRule.java  |   34 [32m+[m
 .../java/seedu/address/ui/testutil/UiPartRule.java |   29 [32m+[m
 .../java/systemtests/AddCommandSystemTest.java     |  248 [32m+++[m
 .../java/systemtests/AddressBookSystemTest.java    |  292 [32m+++[m
 .../java/systemtests/ClearCommandSystemTest.java   |  101 [32m+[m
 src/test/java/systemtests/ClockRule.java           |   57 [32m+[m
 .../java/systemtests/EditCommandSystemTest.java    |  299 [32m+++[m
 src/test/java/systemtests/ErrorDialogGuiTest.java  |   34 [32m+[m
 .../java/systemtests/FindCommandSystemTest.java    |  185 [32m++[m
 .../java/systemtests/HelpCommandSystemTest.java    |  100 [32m+[m
 src/test/java/systemtests/ModelHelper.java         |   39 [32m+[m
 src/test/java/systemtests/SampleDataTest.java      |   51 [32m+[m
 .../java/systemtests/SelectCommandSystemTest.java  |  154 [32m++[m
 .../java/systemtests/SystemTestSetupHelper.java    |   74 [32m+[m
 .../resources/view/UiPartTest/invalidFile.fxml     |    1 [32m+[m
 src/test/resources/view/UiPartTest/validFile.fxml  |    4 [32m+[m
 .../view/UiPartTest/validFileWithFxRoot.fxml       |    6 [32m+[m
 stylesheets/coderay-asciidoctor.css                |   89 [31m-[m
 team/johndoe.html                                  |  247 [31m---[m
 406 files changed, 21407 insertions(+), 5797 deletions(-)

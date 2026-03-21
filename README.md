# ChangeFileNames
Utility in Java to change file names in folder/child-folders in different ways.

## Recent Changes<br>
#### On 02-Aug-2020<br>
Now instead of changing your filenames upfront you can check using "Preview" action that what would be actual changes.  
> Remember that some changes of file names may throw runtime exception, if some operation leads to change in file name that already exists.

## Recent Changes<br>
#### On 03-Jul-2020<br>
Changes made to option/location/params will be auto-saved as preferences.<br>

## Usage<br>
`javac ChangeFileNames <srcDir> <include-sub-folders> <file-filter-extension> <option> [<extra-param>]`<br>
where
 * \<srcDir> - source directory whose filenames are to be processed.<br>
 * \<include-sub-folders> - TRUE to include and FALSE to exclude.<br>
 * \<file-filter-extension> - the specific files (like .html or .class etc) to be processed. "ALL" for all files.<br>
 * \<option> - the operation to be performed on files, also <extra-param> is optional and required by some of the operations of <option>. The valid options are:<br>
        1.  REMOVE_NUMBERS_FROM_FILE_NAMES<br>
        2.  REMOVE_NUMBERS_FROM_START<br>
        3.  REMOVE_NUMBERS_FROM_END<br>
        4.  APPEND_STRING_IN_START <string><br>
        5.  APPEND_STRING_IN_END <string><br>
        6.  REMOVE_CHARS_FROM_START <number-of-chars><br>
        7.  REMOVE_CHARS_FROM_END <number-of-chars><br>
        8.  REMOVE_SPACES_FROM_START<br>
        9.  REMOVE_SPACES_FROM_END<br>
        10. REMOVE_SPACES_FROM_BOTH_SIDES<br>
        11. REMOVE_MATCH_FROM_START <string><br>
        12. REMOVE_MATCH_FROM_END <string><br>
        13. REMOVE_MATCH <string><br>
        14. REPLACE_MATCH_FROM_START <search-string> <replacement-string><br>
        15. REPLACE_MATCH_FROM_END <search-string> <replacement-string><br>
        16. REPLACE_MATCH <search-string> <replacement-string><br>
        17. CONVERT_TO_TITLE_CASE<br>
        18. UPDATE_MP3_TAGS (obsolete)<br>

> Examples 
> 1. `javac ChangeFileNames c:/test FALSE mp3 REMOVE_NUMBERS_FROM_START`<br>
> 2. `javac ChangeFileNames c:/test FALSE ALL REMOVE_NUMBERS_FROM_FILE_NAMES`<br>
> 3. `javac ChangeFileNames c:/test FALSE java APPEND_STRING_IN_START prefix`<br>
> 4. `javac ChangeFileNames c:/test FALSE class REMOVE_CHARS_FROM_END 4`<br>
> 5. `javac ChangeFileNames c:/test FALSE class REMOVE_SPACES_FROM_END`<br>
> 6. `javac ChangeFileNames c:/test FALSE class REPLACE_MATCH abc xyz`<br>

#### Images<br>
Welcome screen that stores all your values from last run<br>
![Image of Yaktocat](https://github.com/svermaji/ChangeFileNames/blob/master/cfn.png)<br> 
<br>
Welcome screen that stores all your values from last run<br>
![Image of Yaktocat](https://github.com/svermaji/ChangeFileNames/blob/master/cfn-preview.png)<br> 
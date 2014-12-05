<?php
require('Parsedown.php');
    $Parsedown = new Parsedown();
    $str = file_get_contents($argv[1]);
    $str = $Parsedown->text($str);
    $str = preg_replace('/<(code|style)(?:(?!<\/\1).)*?<\/\1>/s', '', $str);
    $str = preg_replace("/\```[^)]+\```/", '', $str); //remove all between ```
    print_r(str_word_count(preg_replace('/[^a-z0-9]/i', '_', strip_tags(strtolower($str))), 0));
?>


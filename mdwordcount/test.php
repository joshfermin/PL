<?php
require('Parsedown.php');
    // $str = file_get_contents('output.html', true);
    // $text = strip_tags($str, "<style>");
    // $substring = substr($text,strpos($text,"<style"),strpos($text,"</style>"));
    // // print_r($substring);
    // $text = str_replace($substring, "", $text);
    // print_r($text);
    // $text = str_replace(array("\t","\r","\n"),"",$text);
    // $text = trim($text);
    // // print_r(array_count_values(str_word_count(strtolower($text), 1)));

  $Parsedown = new Parsedown();
    $str = file_get_contents('Lab2.md');
    $str = $Parsedown->text($str);
    $str = preg_replace('/<(code|style)(?:(?!<\/\1).)*?<\/\1>/s', '', $str);
    $str = preg_replace("/\```[^)]+\```/", '', $str); //remove all between ```
    // $str = file_get_contents('https://raw.githubusercontent.com/joshfermin/PL/master/lab2/Lab2.md');
    print_r(array_count_values(str_word_count(preg_replace('/[^a-z0-9]/i', '_', strip_tags(strtolower($str))), 1)));
?>
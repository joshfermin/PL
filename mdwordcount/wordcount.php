<?php
$options = getopt('h');

if (!empty($options)) {
    foreach (array_keys($options) as $option) {
        switch ($option) {
            case 'h':
                // Display help with OK exit code.
                self_usage('hello');
                exit(0);
            default:
                // Display help with ERR exit code.
                self_usage('Too many params');
                exit(1);
        }
    }
}

// Show it
print_r($options);

require('Parsedown.php');
    $Parsedown = new Parsedown();
    $str = file_get_contents($argv[1]);
    $str = $Parsedown->text($str);
    $str = preg_replace('/<(code|style)(?:(?!<\/\1).)*?<\/\1>/s', '', $str);
    $str = preg_replace("/\```[^)]+\```/", '', $str); //remove all between ```
    // print_r(str_word_count(preg_replace('/[^a-z0-9]/i', '_', strip_tags(strtolower($str))), 0));
?>


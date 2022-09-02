<?php
require "Similarity.php";

if (!empty($_GET['T'])) {
    $text = $_GET['T'];
    $text = Conversion($text);

    echo Response($text);
}
else {
    echo "null";
}
function Conversion($text) {
    $text = trim(preg_replace('/\s\s+/', ' ', $text));
    return $text;
}
function Response($text) {
    $OutPut = "";
    $textarr = explode(" ",$text);
    $handle = fopen("list.txt", "r");
    if ($handle) {
        while (($line = fgets($handle)) !== false) {
            $line = explode(",",$line);
            if (similarity($text,$line[0]) >= 80) {
                $OutPut .= $line[1] . " ";
            }
            else {
                foreach ($textarr as $Word) {
                    if (similarity($Word,$line[0]) >= 80) {
                        $OutPut .= $line[1]  . " ";
                    }
                }
            }
        }
        fclose($handle);
    }
    return $OutPut;
}
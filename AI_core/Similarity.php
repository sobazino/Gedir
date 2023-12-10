<?php
//This code is just a test.
function similarity($s1, $s2) {
    $longer = $s1;
    $shorter = $s2;
    if (strlen($s1) < strlen($s2)) {
      $longer = $s2;
      $shorter = $s1;
    }
    $longerLength = strlen($longer);
    if ($longerLength == 0) {
      return 0;
    }
    return ($longerLength - editDistance($longer, $shorter)) / (float)$longerLength *100;
}
function editDistance($s1, $s2) {
    $s1 = strtolower($s1);
    $s2 = strtolower($s2);
    $costs = [];
    for ($i = 0; $i <= strlen($s1); $i++) {
      $lastValue = $i;
      for ($j = 0; $j <= strlen($s2); $j++) {
        if ($i == 0)
          $costs[$j] = $j;
        else {
          if ($j > 0) {
            $newValue = $costs[$j - 1];
            if ($s1[$i - 1] != $s2[$j - 1])
              $newValue = min(min($newValue, $lastValue),
                $costs[$j]) + 1;
            $costs[$j - 1] = $lastValue;
            $lastValue = $newValue;
          }
        }
      }
      if ($i > 0)
        $costs[strlen($s2)] = $lastValue;
    }
    return $costs[strlen($s2)];
  }

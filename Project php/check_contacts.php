<?php
$nameUser = $_POST['nameUser'];
$number = $_POST['number'];

$error = '';
if(trim($nameUser) == '')
   $error = 'Error::Введите свое имя';
else if(trim($number) == '')
   $error = 'Error::Введите номер';
else if(strlen($number) < 11)
   $error = 'Error::Введите номер';

if($error != '') {
header('Location: /contacts.php');
exit;
}

$subject = "=?utf-8?B?".base64_encode("ЗВОНОК заказан с сайта ВИС от $nameUser")."?=";

$headers = "From: vis55_pochta@vis55.ru\r\nReply-to: vis55_pochta@vis55.ru\r\nContent-type: text/html;charset=utf-8\r\n";

mail('katty9999@mail.ru', $subject, $number, $headers);

header('Location: /success_send');
?>
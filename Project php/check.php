<?php
$email = $_POST['email'];
$nameUser = $_POST['nameUser'];
$message = $_POST['message'];

$error = '';
if(trim($email)=='')
   $error = 'Error::Введите ваш email';
else if(trim($nameUser) == '')
   $error = 'Error::Введите свое имя';
else if(trim($message) == '')
   $error = 'Error::Введите сообщение';
else if(strlen($message) < 7)
   $error = 'Error::Введите сообщение';

if($error != '') {
header('Location: /index.php');
exit;
}

$subject = "=?utf-8?B?".base64_encode("Сообщение с сайта ВИС от $nameUser")."?=";

$headers = "From: $email\r\nReply-to: $email\r\nContent-type: text/html;charset=utf-8\r\n";

mail('katty9999@mail.ru', $subject, $message, $headers);

header('Location: /success_send');
?>
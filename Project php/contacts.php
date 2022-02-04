<!DOCTYPE html>
<html lang="ru">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/style_c.css">
<link rel="stylesheet" href="/css/shake.css">
<script src="//web-ptica.ru/VRV-files/fancybox3/1.12.4-jquery.min.js"></script>
<script src="//web-ptica.ru/VRV-files/fancybox3/jquery.maskedinput.min.js"></script>
<script src="//web-ptica.ru/VRV-files/fancybox3/jquery.fancybox.min.js"></script>
<script src="//web-ptica.ru/VRV-files/fancybox3/script-zvonka.js"></script>
<link rel="stylesheet" type="text/css" href="//web-ptica.ru/VRV-files/fancybox3/jquery.fancybox.min.css">   
<link rel="stylesheet" type="text/css" href="//web-ptica.ru/VRV-files/fancybox3/fancyvrv.css">  
<title> Контакты </title>
</head>
<body>
    <?php include('templates/header.php'); ?>
    
    
    <div class="main-contact" >

    <div class="main-image-contacts" align="center" >
    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2287.906044993747!2d73.33285821586274!3d55.00981198036118!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x43ab01d39fd09e79%3A0x7a3be1d57022924d!2z0YPQuy4g0JrRgNCw0YHQvdGL0Lkg0J_Rg9GC0YwsIDE0Mywg0J7QvNGB0LosINCe0LzRgdC60LDRjyDQvtCx0LsuLCA2NDQwMzM!5e0!3m2!1sru!2sru!4v1623269779518!5m2!1sru!2sru" width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
    </div> 
    
   
     
    
    <div class="contacts">
       <h>Реквизиты: </h><br>
       <a>Название: ООО "ВентИнвестСтрой"</a><br>
       <a>ИНН: 5501263088</a><br>
       <a>ОГРН: 1205500006711</a><br>
       <a>ОКВЭД: 43.22</a><br>
       <a>ОКПО: 43681915</a><br>
       <a>Кор. счет: 30101810600000000774</a><br>
       <a>КПП: 550101001</a><br>
       <a>БИК: 045004774</a><br>
       <a>Номер счета: 40702810323320001356</a><br><br><br>
    </div> 
    
    <div class="contacts">
        <h>Контакты: </h> <br>
        <a>Телeфон: +7 (3812) 518-418</a><br>
        <a>E-mail: vis55@list.ru</a><br>
        <a>Адрес: г.Омск, ул. Красный Путь 143, офис 126</a><br>
        <a>Режим работы: ПН.-ПТ. с 9:00 до 18:00</a><br><br><br>
    </div> 
    
    <div class="contacts">
         <li><img  class="shake-slow"  src="images/telephone.svg" /><br></li>
         <a href="#modal" class="button openModal">Заказать обратный звонок</a> 
    </div>
    <br><br>

</div>
   
    
<div id="modal" class="modal">
    <div>
       <div class="text" align="center">
           <div id="blok_tel">
               <form action="check_contacts.php" method="post">
                   <h>Заказать обратный звонок:</h> <br>
                <input type="text" name="nameUser" placeholder="Ваше имя" class="formNameC" required><br>
                <input type="tel" pattern="+7-[0-9]{11}" name="number" placeholder="+7 xxx xxx xx xx" class="formNumber" required><br>
                <button type="submit" name="send" class="call" >Перезвоните мне!</button>
                </form>
            </div>
        </div>
        <a href="#close" title="Закрыть">Закрыть</a>
    </div>        
</div>

   <?php include('templates/footer.php'); ?>
</body>
</html>
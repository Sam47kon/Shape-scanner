# Задание №1
##### Создать приложение, которое сможет считывать из файла набор фигур (должно быть не менее 4 различных типов фигур, и при этом хоть один параметр общий). Желательно использовать абстрактную фабрику.
###### Доступны должно быть следующие действия:
1) повернуть на
2) переместить на
3) увеличить/ уменьшить в
4) рассчитать площадь

По Вашему усмотрению программа может быть с выводом графическим ( консоль, графическое приложение, веб-проект), либо без (таким образом на вход файл - результат тоже файл)


## Реализация 1
Реализовано с помощью Абстрактной фабрики.

Первым делом программа считывает данные с файла `template.txt`, построчно парсит и создает соответствующую фигуру.

###### Пользователю доступно меню с выбором действий:
1) Показать характеристики всех фигур
2) Выбрать конкретную фигуру (для действий над ней)
    1) Повернуть
    2) Переместить
    3) Увеличить
    4) Уменьшить
    5) Рассчитать площадь
    6) Удалить фигуру
    6) Вернуться назад
3) Создать фигуру
4) Сохранить изменения в файле
5) Выход

## Реализация 2
Реализовано с помощью  REST API на MongoDB.

Запуск: 
1. Включить обработку аннотаций:  Inrelij IDEA -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> ***Enabled Annotation Processing***
2. Установить Lomboc плагин: Inrelij IDEA -> Settings -> Plugins -> ***Lomboc*** *Установить*
3. Запуск проекта: com.app.restapp.Main
4. API Cals -> [Примеры запросов PostMan](https://documenter.getpostman.com/view/8128788/TVt17PFY) 

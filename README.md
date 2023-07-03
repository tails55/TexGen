# TexGen: Генератор экзаменационных работ по дискретной математике и математической логике

*TODO: написать нормальный README.md вместо плейсхолдера*

## О программе
Программа была разработана в 2020-2021 году как моя ВКР на бакалавриате. Она позволяет генерировать экзаменационные работы по ДММЛ с решениями по указанным шаблонам. Пользователь может задавать типы и параметры генерации задач во входном .xml файле, оформление (вроде заголовка) во входных .tex файлах и параметры вроде количества вариантов в аргументах командной строки или GUI.

Проверить программу на примере можно, запустив класс бэкенда **ru.omsu.imit.tails55.texgen.backend.io.IOClass** со следующими аргументами командной строки:
*"sample.xml" "sample.tex" "answers.tex" "output.tex" "output-answers.tex"*


# План

**1.Перечень автоматизированных сценариев**

**Позитивные**
- карта 4444 4444 4444 4441
    * покупка успешна
    * покупка в кредит успешна
- карта 4444 4444 4444 4442
    * покупка отказана
    * покупка в кредит отказана

**Негативные**
- Ввод невалидных значений в поле Месяц (Покупка/Кредит)
- Ввод невалидных значений в поле Год (Покупка/Кредит)
- Ввод невалидных значений в поле Номер карты (Покупка/Кредит)
- Ввод невалидных значений в поле Владелец (Покупка/Кредит)
- Ввод невалидных значений в поле CVC/CVV (Покупка/Кредит)
- Проверка на пустое поле

**2. Перечень используемых инструментов**

IntelliJ IDEA Ultimate - программа, в которой пишется код.

Gradle - инструмент управления зависимостями.

JUnit 5 - библиотека для тестирования.

Selenide - работа с веб-страницами. Позволяет искать появившиеся значения с помощью html и css. Можно описать как инструмент для автоматизации работы с браузером

Allure - используется для наглядного изображения прохождения тестов и ошибок.

Faker - система генерации случайных данных для тестов. Позволяет генерировать различные данные автоматически с учетом местоположения

Git и Github - система контроля версий. Удобство, возможность одновременной параллельной разработки, есть интеграция с IntelliJ IDEA

Java 11 - универсальный язык, позволяющий работать на всех ОС и различном оборудовании за счет виртуальной машины, которая полностью контролирует безопасность и прерывает работу если что-то пошло не так (несанкционированный доступ и пр.)

Lombok - плагин для создания аннотаций. Тут, скорее, плагин для использования аннотаций вместо стандартных конструкций java - конструкторы, геттеры, сеттеры

Docker — легковесная платформа контейнерной виртуализации, использует процессы и утилиты, которые помогают управлять и выкладывать наши приложения

MySQL и PostgreSQL для проверки работоспособности системы в различных БД.

**3.Перечень и описание возможных рисков при автоматизации**

3.1 Проблемы с запуском приложения, подключением БД;

3.2 Сложности в настройке запуска SUT с различными параметрами.

3.3 Отсутствие спецификации на приложение;

3.4 Зависимость авто-тестов от текущей реализации веб-элементов, даже не значительное их изменение может привести к падению авто-тестов;

**4.Интервальная оценка с учётом рисков (в часах)**

4.1 Разработка плана тестирования - 5 часов;

4.2 Подготовка необходимых инструментов, написание кода автотестов - 72 часа;

4.3 Подготовка отчетной документации, баг-репортов - 24 часа;

4.4 Запас в виде 72 часов на форс-мажор обстоятельства.

**5. План сдачи работ**

5.1 Сдача плана тестирования - 11.11.2022 (3 рабочих дня на утверждения плана и разрешения вопросов)

5.2 Готовность автотестов - 26.11.2022 (3 рабочих дня на утверждение автотестов и разрешения вопросов)

5.3 Результат работы автотестов - 30.11.2022 документы по итогам тестирования (отчет по итогам тестирования + баг-репорты)

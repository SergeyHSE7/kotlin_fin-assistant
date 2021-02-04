[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)


# FinAssistant

Данный сервис служит для предоставления информации по акциям иностранных и российских компаний. 

Для написания данного сервиса было использовано официальное руководство [Creating HTTP APIs with Ktor](https://play.kotlinlang.org/hands-on/Creating%20HTTP%20APIs%20with%20Ktor), 
[Currency API](https://www.currency-api.com/) и 
[OpenAPI от Тинькоффа](https://tinkoffcreditsystems.github.io/invest-openapi/). 

Перед запуском проекта необходимо добавить переменную окружения *api_token* (токен OpenAPI для Sandbox от Тинькоффа).

### Установка данного проекта:
1. Скачайте проект и откройте его в IntelliJ Idea IDE
2. Убедитесь, что у вас настроен JDK 11 или выше
3. Дождитесь пока Gradle загрузит все зависимости для проекта
4. Запустите проект через функцию main в файле *src\main\kotlin\com\kotlinHSE\sergey\Application.kt*
5. **Profit!**

### Описание предоставляемого API:
- **/api/stocks/{period}/{sortCriteria}/{isDescending}** - возвращает отсортированный список акций с историей цен за указанный период.
- **/api/stocks/{figi}/{period}** - возвращает информацию об одной акции с указанным индентификатором (FIGI - глобальный идентификатор финансового инструмента), а также историю цен за указанный период.

Разработчик: *Елесин Сергей Владимирович* 

# WebFluxCalculator

реактивный RESTful WebFlux-сервис "Калькулятор", выполняющий периодические вычисления
двух произвольных пользовательских функций, заданных в текстовом виде на JavaScript либо Python, и
транслирующий результаты в виде потокового CSV-ответа.

## Технологии
- Java 17
- Spring WebFlux
- Project Reactor
- JavaScript / Python ScriptEngine
- Lombok
- Maven

## Использование API

Метод: GET

URL: /api/calculate

Query параметры:
- count (int) — количество итераций
- ordered (boolean) — признак упорядоченности вывода результатов:
    - true — упорядоченный (синхронизированный вывод):
    - false — неупорядоченный (асинхронный вывод)

## Запуск

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/yourusername/webflux-calculator.git
cd webflux-calculator
```

### 2. Запустите приложение

```
./mvnw spring-boot:run
```

### 3. Проверьте работу приложения 

Перейдите в браузере по ссылке http://localhost:8080/api/calculate?count=50&ordered=false


## Конфигурация

Параметры можно изменить в config.json каталога resources:

JavaScript:
```
{
  "function1": "function(x) { return x * x; }",
  "function2": "function(x) { return x + 10; }",
  "interval": 10
}
```

Python:
```
{
  "function1": "return x * x",
  "function2": "return x + 10",
  "interval": 10
}
```

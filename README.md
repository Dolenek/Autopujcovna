<!DOCTYPE html>
<html lang="cs">
<head>
    <meta charset="UTF-8">
</head>
<body>

<h1>Autopůjčovna</h1>

<h2>Popis projektu</h2>

<p>Autopůjčovna je backendová aplikace pro správu vozového parku a půjčování vozidel zákazníkům. Aplikace umožňuje správu vozidel, zákazníků a záznamů o půjčení vozidel prostřednictvím RESTful API. Cílem projektu je poskytnout efektivní a udržovatelné řešení s důrazem na čistý kód.</p>

<h2>Funkcionalita</h2>

<ul>
    <li><strong>Správa vozového parku</strong>
        <ul>
            <li>Přidávání, úprava a mazání vozidel.</li>
            <li>Každé vozidlo obsahuje atributy: id, značka, model, rok výroby, barva a stav kilometrů.</li>
        </ul>
    </li>
    <li><strong>Záznam půjčení vozidel</strong>
        <ul>
            <li>Zaznamenávání půjčení vozidla zákazníkem.</li>
            <li>Historie půjčení pro každého zákazníka.</li>
            <li>Historie půjčení každého vozidla.</li>
        </ul>
    </li>
    <li><strong>Správa zákazníků</strong>
        <ul>
            <li>Přidávání nových zákazníků.</li>
            <li>Zobrazení seznamu zákazníků.</li>
            <li>Uchování základních informací: jméno, kontaktní údaje, historie půjčených vozidel.</li>
        </ul>
    </li>
</ul>

<h2>Technologický stack</h2>

<ul>
    <li><strong>Java 11+</strong></li>
    <li><strong>Spring Boot</strong>
        <ul>
            <li>Spring Web MVC</li>
            <li>Spring Data JPA</li>
            <li>Spring Validation</li>
        </ul>
    </li>
    <li><strong>PostgreSQL Database</strong> (pro vývoj a testování)</li>
    <li><strong>Swagger/OpenAPI</strong> (pro dokumentaci API)</li>
    <li><strong>Gradle</strong>(pro build a závislosti)</li>
    <li><strong>JUnit 5</strong> a <strong>Mockito</strong> (pro testování)</li>
</ul>

<h2>Požadavky</h2>

<ul>
    <li><strong>Java Development Kit (JDK) 11+</strong></li>
    <li><strong><strong>Gradle</strong></li>
    <li><strong>Git</strong> (pro klonování repozitáře)</li>
</ul>

<h2>Instalace a spuštění</h2>

<ol>
    <li><strong>Klonování repozitáře</strong>
        <pre><code>git clone https://github.com/Dolenek/autopujcovna.git
cd autopujcovna</code></pre>
    </li>
    <li><strong>Build projektu</strong>
        <ul>
                <pre><code>./gradlew build</code></pre>
        </ul>
    </li>
    <li><strong>Spuštění aplikace</strong>
        <ul>
                <pre><code>./gradlew bootRun</code></pre>
        </ul>
    </li>
    <li><strong>Přístup k aplikaci</strong>
        <ul>
            <li>API je dostupné na: <code>http://localhost:8080/api</code></li>
            <li>Dokumentace Swaggeru: <code>http://localhost:8080/swagger-ui/</code></li>
        </ul>
    </li>
</ol>

<h2>Použití</h2>

<h3>API Endpointy</h3>

  <h4>Půjčení</h4>
<ul>
    <li>
        <code>POST /api/v1/pujceni/pujcit</code><br>
        Půjčí vozidlo zákazníkovi.
    </li>
    <li>
        <code>POST /api/v1/pujceni/vratit</code><br>
        Vrátí půjčené vozidlo.
    </li>
    <li>
        <code>GET /api/v1/pujceni/zakaznik/{zakaznikId}</code><br>
        Získá historii půjčení podle zákazníka.
    </li>
    <li>
        <code>GET /api/v1/pujceni/vozidlo/{vozidloId}</code><br>
        Získá historii půjčení podle vozidla.
    </li>
    <li>
        <code>GET /api/v1/pujceni/historie/vozidla</code><br>
        Získá historii všech půjček.
    </li>
</ul>

<h4>Zákazník</h4>
<ul>
    <li>
        <code>GET /api/v1/zakaznik</code><br>
        Získá seznam všech zákazníků.
    </li>
    <li>
        <code>POST /api/v1/zakaznik/pridat</code><br>
        Přidá zákazníka do seznamu.
    </li>
    <li>
        <code>DELETE /api/v1/zakaznik/odstranit/{zakaznikId}</code><br>
        Pomocí ID odstraní zákazníka ze seznamu.
    </li>
    <li>
        <code>PUT /api/v1/zakaznik/aktualizovat/{zakaznikId}</code><br>
        Aktualizuje údaje zákazníka.<br>
        <em>Parameters (optional):</em> <code>jmeno (String)</code>, <code>email (String)</code>, <code>telefon (String)</code>
    </li>
    <li>
        <code>GET /api/v1/zakaznik/historie/{zakaznikId}</code><br>
        Získá historii všech půjčených aut od zákazníka.
    </li>
</ul>

<h4>Vozidlo</h4>
<ul>
    <li>
        <code>GET /api/v1/vozidlo</code><br>
        Získá seznam všech vozidel.
    </li>
    <li>
        <code>POST /api/v1/vozidlo/pridat</code><br>
        Přidá vozidlo do seznamu.
    </li>
    <li>
        <code>PUT /api/v1/vozidlo/aktualizovat/{vozidloId}</code><br>
        Aktualizuje údaje vozidla.<br>
        <em>Parameters (optional):</em> <code>barva (String)</code>, <code>stavKilometru (Integer)</code>, <code>dostupnost (Boolean)</code>
    </li>
    <li>
        <code>DELETE /api/v1/vozidlo/odstranit/{vozidloId}</code><br>
        Pomocí ID odstraní vozidlo ze seznamu.
    </li>
    <li>
        <code>GET /api/v1/vozidlo/nevypujcena</code><br>
        Získá seznam všech nevypůjčených vozidel.
    </li>
    <li>
        <code>GET /api/v1/vozidlo/vypujcena</code><br>
        Získá seznam všech vypůjčených vozidel.
    </li>
</ul>
<h3>Příklady použití API</h3>

<h4><strong>Vytvoření nového vozidla</strong></h4>

<pre><code>POST /api/vozidla
Content-Type: application/json

{
  "znacka": "Škoda",
  "model": "Octavia",
  "rokVyroby": 2020,
  "barva": "Modrá",
  "stavKilometru": 15000
}</code></pre>

<h4><strong>Získání historie půjčení zákazníka</strong></h4>

<pre><code>GET /api/zakaznici/1/historie</code></pre>

<h4><strong>Zaznamenání nového půjčení</strong></h4>

<pre><code>POST /api/pujceni
Content-Type: application/json

{
  "vozidlo": { "id": 2 },
  "zakaznik": { "id": 1 },
  "datumPujceni": "2023-10-01",
  "datumVraceni": "2023-10-10"
}</code></pre>

<h2>API Dokumentace</h2>

<p>Kompletní dokumentace API je dostupná pomocí Swaggeru na adrese:</p>

<ul>
    <li><strong>Swagger UI</strong>: <a href="http://localhost:8080/swagger-ui/">http://localhost:8080/swagger-ui/</a></li>
    <li><strong>OpenAPI Specifikace</strong>: <a href="http://localhost:8080/v3/api-docs">http://localhost:8080/v3/api-docs</a></li>
</ul>

</body>
</html>

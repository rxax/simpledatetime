# Java Date/Time was naïve

why is java date time such a mess?
Because Java has not one date/time API, but several generations of them stacked on top of each other.

### 1. `java.util.Date` was a bad abstraction

The original `Date` is basically a wrapper around a timestamp, but its API contains bizarre legacy methods:

```text
Date date = new Date();
date.getYear();   // years since 1900
date.getMonth();  // 0 = January
```

It doesn't really represent a human concept like "August 20, 2026 at 10:30 in Romania."

### 2. Then came `Calendar`

Java tried to fix `Date` with:

```text
Calendar calendar = Calendar.getInstance();
calendar.set(2026, Calendar.AUGUST, 20);
```

Except:

- months are zero-based
- `Calendar` is mutable
- the API is enormous
- time zones are awkward
- fields can interact in surprising ways
- it's easy to accidentally construct invalid/ambiguous dates

### 3. Java 8 basically admitted defeat

Java 8 introduced `java.time`, based heavily on the much better Joda-Time design.

Now you have:

```text
LocalDate
LocalTime
LocalDateTime
ZonedDateTime
OffsetDateTime
Instant
Year
YearMonth
MonthDay
Duration
Period
ZoneId
ZoneOffset
DateTimeFormatter
```

These are individually quite good. The problem is that there are so many of them.

For example:

```text
LocalDateTime
```

looks like it should represent a complete date/time, but it has no time zone.

While:

```text
ZonedDateTime.now()
```

means a date/time in a specific time zone.

And:

```text
Instant.now()
```

means an exact point on the global timeline.

These are fundamentally different concepts, but beginners naturally expect them all to just be "a date."

### 4. Time zones are genuinely horrible

Java has to deal with:

```text
Europe/Bucharest
America/New_York
Asia/Tokyo
```

rather than simply:

```text
UTC+2
```

because time zones aren't fixed offsets.

Bucharest can be UTC+2 or UTC+3 depending on daylight-saving rules, and those rules have changed historically.

### 5. The really annoying part: conversions

You constantly encounter conversions such as:

```text
LocalDateTime
    ↓
ZoneId
    ↓
ZonedDateTime
    ↓
Instant
```

or:

```text
Date
    ↓
Instant
    ↓
ZonedDateTime
```

The conversion depends on whether you're talking about:

- a calendar date
- a wall-clock time
- an instant
- an offset
- a time zone
- a duration
- a period

### The irony

The Java 8+ API is actually pretty good.

The problem is that Java has to coexist with:

```text
Date
Calendar
GregorianCalendar
java.sql.Date
java.sql.Time
java.sql.Timestamp
```

plus:

```text
java.time.*
```

plus third-party libraries such as Joda-Time.

So modern Java date/time code often looks like a historical archaeological dig.

### Rule of thumb

For modern Java:

| What you mean | Use |
|---|---|
| Just a date | `LocalDate` |
| Just a time | `LocalTime` |
| Date + time, no timezone | `LocalDateTime` |
| Exact moment in time | `Instant` |
| Date + time + timezone | `ZonedDateTime` |
| Date + time + fixed offset | `OffsetDateTime` |
| Amount of elapsed time | `Duration` |
| Calendar amount like "3 months" | `Period` |
| Timezone | `ZoneId` |

Avoid `Date` and `Calendar` in new code unless interfacing with legacy APIs.

---

# Date time alternatives

Today I wouldn't replace `java.time` wholesale. I'd use a library that makes the parts of `java.time` that annoy you nicer.

### My ranking

| Library | Why |
|---|---|---|
| **ThreeTen-Extra** | Best companion to `java.time` |
| **Time4J** |  Powerful alternative if you really want a richer abstraction |
| **Joda-Time** | Excellent historically, but essentially legacy for Java 8+ |

### 1. ThreeTen-Extra — first choice

ThreeTen-Extra is basically "the missing pieces of `java.time`." It was created specifically to complement the Java 8 date/time API.

It gives you useful concepts such as:

```text
YearQuarter
YearWeek
HourMinute
Interval
LocalDateRange
PeriodDuration
Days
Weeks
Months
```

For example:

```text
LocalDateRange range =
    LocalDateRange.of(start, end);
```

And:

```text
YearWeek week = YearWeek.of(2026, 34);
YearQuarter quarter = YearQuarter.of(2026, 3);
```

That's exactly the sort of thing that could have been included in `java.time`.

### 2. Time4J — if you want to go further

Time4J is much more ambitious. It provides a complete alternative date/time system, with support for things such as:

- more calendar systems
- advanced intervals
- additional time scales
- leap seconds
- historical dates
- astronomy-related calculations

It also interoperates with `java.time`.

I'd consider it if you're building something where date/time is a core domain problem rather than just something your application happens to store in a database.

For normal Spring/JPA applications, though, it's probably overkill.

### 3. Joda-Time — don't start a new project with it

Joda-Time was hugely important because `java.time` was heavily influenced by it.

But its maintainers recommend migrating to `java.time` for Java 8+. Joda-Time is essentially a finished project, with maintenance focused mainly on timezone data.

So:

```text
Old Java project → Joda-Time → java.time
```

not:

```text
New Java project → Joda-Time
```

### What I'd actually use

For a modern Java/Spring project:

```text
                    java.time
                       │
          ┌────────────┴────────────┐
          │                         │
      ThreeTen-Extra            java.time
          │
    missing concepts
    ├── YearWeek
    ├── YearQuarter
    ├── Interval
    ├── LocalDateRange
    └── better amounts
```

So **`java.time` + ThreeTen-Extra** is probably the sweet spot.

If the goal is "a simpler Java date API where I don't have to remember which of the 17 temporal classes I need", a small domain-level wrapper around `Instant`, `LocalDate`, and `ZoneId` can be preferable to introducing Time4J.

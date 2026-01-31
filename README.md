# UltraScoreboard

A modern and flexible scoreboard library for Hytale servers, providing an easy-to-use API for creating and managing custom scoreboards with support for dynamic content and multiple rendering systems.

## Features

- 🎯 Simple and intuitive API for scoreboard creation
- 🔄 Dynamic line updates and custom rendering
- 🎨 Support for custom line alignments and formatting
- 🔌 Pluggable renderer system
- ⚡ Efficient update system with configurable tick delays
- 🎭 Multiple HUD dependence implementations (Default, Buzz, Flash)

## Installation

### Gradle (Kotlin DSL)

Add JitPack repository:
```kotlin
repositories {
    maven("https://jitpack.io")
}
```

Add the dependency:
```kotlin
dependencies {
    implementation("com.github.Neiy0:UltraScoreboard:v1.0.1")
}
```

### Gradle (Groovy)

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Neiy0:UltraScoreboard:v1.0.1'
}
```

### Maven

Add JitPack repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add the dependency:
```xml
<dependency>
    <groupId>com.github.Neiy0</groupId>
    <artifactId>UltraScoreboard</artifactId>
    <version>v1.0.1</version>
</dependency>
```

## Quick Start

### 1. Initialize the library

```java
import fr.neiyo.scoreboard.UltraScoreboard;

public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        UltraScoreboard.initialize(this);
    }
}
```

### 2. Create a custom scoreboard

```java
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.line.impl.SimpleLine;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public class MyScoreboard extends Scoreboard {
    
    public MyScoreboard(PlayerRef player) {
        super(player);
    }
    
    @Override
    public void setLines() {
        setTitle(Message.of("My Server"));
        
        addLine(new SimpleLine(Message.of("Player: " + player.getName())));
        addLine(new SimpleLine(Message.empty())); // Empty line
        addLine(new SimpleLine(Message.of("Online: 42")));
    }
}
```

### 3. Assign scoreboard to a player

```java
import fr.neiyo.scoreboard.api.ScoreboardProvider;

PlayerRef player = ...;
MyScoreboard scoreboard = new MyScoreboard(player);
ScoreboardProvider.get().setScoreboard(player, scoreboard);
```

## How It Works

UltraScoreboard operates on a system-based architecture:

1. **Initialization**: The library registers a `ScoreboardSystem` into the Hytale entity store registry
2. **Scoreboard Creation**: You extend the `Scoreboard` class and implement the `setLines()` method
3. **Assignment**: Scoreboards are assigned to players via the `ScoreboardProvider`
4. **Update Cycle**: The system automatically updates scoreboards at a configured tick rate (default: 0.1s)
5. **Rendering**: A pluggable renderer system (default: `MinecraftScoreboardRenderer`) handles the visual display

The library supports:
- Custom line implementations with alignment support
- Dynamic content updates through the `setLines()` method
- Multiple HUD dependence strategies for different display behaviors
- Custom renderers for specialized scoreboard displays

## License

This project is licensed under the terms specified in the repository.

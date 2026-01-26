-> RPE - Role Playing Engine

    A modular and extensible 2D Game Engine in Java, designed to separate game logic, physics and rendering through a robust component-based structure.

-> Tech Stack

    Language: Java (JDK 17+)

    Data Handling: GSON (JSON parsing)

-> Key Features

    .Core Engine: Life cycle managed by an independent GameEngine with FPS and DeltaTime control.

    .Generic Object Loader: Data-driven system that instantiates game objects via JSON configuration files, allowing for easy content creation without recompiling.

    .Input Management: Abstracted keyboard event handler to simplify control mapping and state management.

-> Project Structure

    ./src/GameEngine/: Core Framework (Physics, Input, Life-Cycle).
    ./src/Game/: Specific game implementation and logic.
    ./resources/: External JSON configs and sprite assets.

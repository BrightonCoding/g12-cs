# Pong UML class diagram

```mermaid
classDiagram
    Game <|-- Pong
    JFrame <|-- Game
    GameObject <|-- Ball
    GameObject <|-- Paddle
    JComponent <|-- GameObject
    Pong --> "1" Ball : ball
    Pong --> "2" Paddle : p1, p2
    Pong --> "2" JLabel : p1Label, p2Label

    class Pong {
        -int DELAY$
        -int WINNING_SCORE$
        -int LABEL_WIDTH$
        -int LABEL_HEIGHT$
        -int LABEL_MARGIN$
        -Ball ball
        -Paddle p1
        -Paddle p2
        -int p1Score
        -int p2Score
        -JLabel p1Label
        -JLabel p2Label
        +setup() void
        +act() void
        -resetBall() void
        -checkWinner() void
        +main(String[] args)$ void
    }

    class Ball {
        -int BALL_SIZE$
        -int BALL_SPEED$
        -int dx
        -int dy
        +Ball()
        +act() void
        +reverseX() void
        +reverseY() void
    }

    class Paddle {
        -int fieldHeight
        -int PADDLE_SPEED$
        -int PADDLE_WIDTH$
        -int PADDLE_HEIGHT$
        +Paddle(int height)
        +act() void
        +moveUp() void
        +moveDown() void
    }

    class Game {
        <<abstract, external library>>
        -boolean _isSetup
        -boolean _initialized
        -ArrayList _ObjectList
        -Timer _t
        -boolean p1Left
        -boolean p1Right
        -boolean p2Left
        -boolean p2Right
        +Game()
        +ZKeyPressed() boolean
        +XKeyPressed() boolean
        +NKeyPressed() boolean
        +MKeyPressed() boolean
        +setup()* void
        +act()* void
        +initComponents() void
        +add(GameObject) void
        +remove(GameObject) void
        +setDelay(int) void
        +setBackground(Color) void
        +startGame() void
        +stopGame() void
        +p1Wins() void
        +p2Wins() void
        +getFieldWidth() int
        +getFieldHeight() int
    }
    class GameObject {
        <<abstract, external library>>
        ~Color c
        +GameObject()
        +setSize(int, int) void
        +getX() int
        +getY() int
        +setX(int) void
        +setY(int) void
        +setColor(Color) void
        +paint(Graphics) void
        +collides(GameObject) boolean
        +act()* void
    }
    class JFrame {
        <<Swing>>
    }
    class JComponent {
        <<Swing>>
    }
    class JLabel {
        <<Swing>>
    }
```

`Game` and `GameObject` were inspected in `csta.ibm.pong.v15.jar`. `JFrame`, `JComponent`, and `JLabel` come from Swing. The `$` marker denotes a static member; `*` denotes an abstract method. Compiler-generated `access$` methods are omitted.

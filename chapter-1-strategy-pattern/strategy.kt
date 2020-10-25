fun main() {
    println("*** Setting up Duck 1: ***")
    with(Duck(CannotFlyBehaviour(), CannotQuackBehaviour())) {
        quack()
        fly()
    }
    println("*** Setting up Duck 2: ***")
    with(Duck(StandardFlyBehaviour(), StandardQuackBehaviour())) {
        quack()
        fly()
    }
    println("*** Setting up Duck 3: ***")
    with(Duck(StandardFlyBehaviour(), CannotQuackBehaviour())) {
        quack()
        fly()
    }
}

class Duck(var flyBehaviour: IFlyBehaviour, 
            var quackBehaviour: IQuackBehaviour) {
    fun quack() {
        quackBehaviour.quack()
    }
    fun fly(){
        flyBehaviour.fly()
    }
}

// Behaviours
interface IQuackBehaviour {
    fun quack()
}
interface IFlyBehaviour {
    fun fly()
}

// Quack behaviour Implementations
class CannotQuackBehaviour(): IQuackBehaviour {
    override fun quack() {
        println("Apparently this duck cannot quack")
    }
}
class StandardQuackBehaviour(): IQuackBehaviour {
    override fun quack() {
        println("Quack!")
    }
}

// Fly behaviour implementations
class StandardFlyBehaviour(): IFlyBehaviour {
    override fun fly() {
        println("Standard fly")
    }
}
class CannotFlyBehaviour(): IFlyBehaviour {
    override fun fly() {
        println("Trying to fly but nothing happens")
    }
}


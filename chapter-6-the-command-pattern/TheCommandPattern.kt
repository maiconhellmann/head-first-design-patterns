fun main() {
    val remote = RemoteControl()
    
    val livingRoomLight = Light("Living room")
    val kitchenLight = Light("Kitchen light")
    val stereo = Stereo("Bedroom")
    
    val livingRoomLightOnCommand = LightOnCommand(livingRoomLight)
    val livingRoomLightOffCommand = LightOffCommand(livingRoomLight)
    val kitchenRoomLightOnCommand = LightOnCommand(kitchenLight)
    val kitchenRoomLightOffCommand = LightOffCommand(kitchenLight)
    val stereoOnCommand = StereoOnCommand(stereo)
    val stereoOffCommand = StereoOffCommand(stereo)
    
    remote.setCommand(0, livingRoomLightOnCommand, livingRoomLightOffCommand)
    remote.setCommand(1, kitchenRoomLightOnCommand, kitchenRoomLightOffCommand)
    remote.setCommand(2, stereoOnCommand, stereoOffCommand)
    
    remote.onButtonPushed(0)
    remote.offButtonPushed(0)
    remote.onButtonPushed(1)
    remote.offButtonPushed(1)
    remote.onButtonPushed(2)
    remote.offButtonPushed(2)
    remote.undoButtonPushed()
}
class RemoteControl() {
    
    val noCommand = object: Command{
        override fun execute(){}
        override fun undo(){}
    }
    
    val onCommands= ArrayList<Command>(7)
    val offCommands= ArrayList<Command>(7)
    var undoCommand: Command = noCommand
    	set(value) {
            field = value
        }
    
    init {
        for (i in 0..7) {
            onCommands.add(noCommand)
            offCommands.add(noCommand)
        }
        undoCommand = noCommand
    }
    
    fun setCommand(slot: Int, onCommand: Command, offCommand: Command){
        onCommands[slot] = onCommand
        offCommands[slot] = offCommand
    }
    
    fun onButtonPushed (slot: Int){
        onCommands[slot].execute()
        undoCommand = offCommands[slot]
    }
    
    fun offButtonPushed (slot: Int){
        offCommands[slot].execute()
        undoCommand = offCommands[slot]
    }
    
    fun undoButtonPushed(){
        undoCommand.undo()
    }
    
    override fun toString(): String{
        val sb = StringBuffer()
        sb.append("\n------- Remote Control --------\n")
        
        for (i in 0..7) {
            sb.append("[slot ${i}] ${onCommands[i]::class.java.getName()}  ${offCommands[i]::class.java.getName()}\n")
        }
        sb.append("Undo command: ${undoCommand::class.java.getName()}")
        return sb.toString()
    }
}
interface Command {
    fun execute()
    fun undo()
}
data class Light(val room: String){
    fun on(){ println("$room light is on") }
    fun off(){ println("$room light is off") }
}
data class Stereo(val room: String){
    var volume: Int?= null
    	set(value){
            field = value
            println("$room stereo volume set to $value")
        }
    
    var type: Type?= null
    	set(value) {
            field = value
            println("$room stereo is set for $value")
        }
    
    fun on(){ println("$room stereo is on") }
    fun off(){ println("$room stereo is off") }
    enum class Type {
        CD, RADIO
    }
    override fun toString(): String {
        return "room=$room, volume=$volume, type=$type"
    }
    fun copy(): Stereo{
        return Stereo(room).also{
            it.volume = volume
            it.type = type
        }
    }
}

class LightOnCommand(val light: Light): Command {
    override fun execute(){
        light.on()
    }
    override fun undo(){
        light.off()
    }
}
class LightOffCommand(val light: Light): Command {
    override fun execute(){
        light.off()
    }
    override fun undo(){
        light.on()
    }
}
class StereoOnCommand(val stereo: Stereo): Command{
    var prevState: Stereo?= null
    
    override fun execute(){
        prevState = stereo.copy()
        stereo.on()
        stereo.type = Stereo.Type.RADIO
        stereo.volume = 12
    }
    override fun undo(){
        stereo.also{
            it.volume = prevState?.volume
            it.type = prevState?.type
            it.off()
        }
    }
}
class StereoOffCommand(val stereo: Stereo): Command {
    var prevState: Stereo?= null
    
    override fun execute(){
        prevState = stereo.copy()
        stereo.off()
    }
    override fun undo(){
        stereo.also{
            it.volume = prevState?.volume
            it.type = prevState?.type
            it.off()
        }
    }
}


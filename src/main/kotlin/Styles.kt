import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass

class Styles : Stylesheet() {
    companion object {
        val menuOneStyle by cssclass()
        val menuTwoStyle by cssclass()
        val menuThreeStyle by cssclass()
	}
    init {
        menuOneStyle {
            backgroundColor += c("red")
        }
        menuTwoStyle {
            backgroundColor += c("blue")
        }

        menuThreeStyle {
            backgroundColor += c("black")
        }
    }
}
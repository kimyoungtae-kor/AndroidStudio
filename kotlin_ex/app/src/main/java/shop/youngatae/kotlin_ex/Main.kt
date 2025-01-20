package shop.youngatae.kotlin_ex

fun main() {
    println("hellow world")
    name();
    ex()
    ifmun()
}
fun name() {
    println("name 김용태");
}
fun ex() {
    var num1 = 10;
    //val 이 const
    val num2 = 15;
    num1 = 20;
//    num2 = 30; < <오류
    println(num1)
    println(num2)
}
fun ifmun() {
    var num1 = 5
    var num2 = 10
    if(num1 > num2){
        println("num1 Win")
    }else{
        println("num2 Win")
    }
}
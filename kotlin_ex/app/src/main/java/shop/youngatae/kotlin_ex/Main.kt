package shop.youngatae.kotlin_ex

fun main() {
    //var,val
    var num1:Int = 5;
    var num2 = 30;
    var num3 = num1+num2
    var num4:Byte;

    println(num3);

    if(num1 > num2){
        println("num1 이 더큼 ")
    }else{
        println("num2 가 더큼")
    }

    //when
    when(num1) {
        5 -> println("value 5")
        10 -> println("value 10")
        else -> println("invalid value")
    }

    var result = when(num3){
        10  -> "value is 10"
        15 -> "value is 15"
        else -> "unknown"

    }
    println(result)

    val day = "화"
    when(day){
        "월","화","수","목","금" -> println("weekday")
        "토","일" -> println("weekend")
    }
    //범위 조건
    //score = 85
    // A B C
    var score:Int = 85;
    if(score >= 90) {
        println("A")
    }else if(score >= 80){
        println("B")
    }else{
        println("C")
    }

    when(score){
        in 90..100 -> println("A")
        in 80 .. 89 -> println("B")
        else -> println("c")
    }

    println(score is Int)
    var value:Any = "Hello"
    result = when(value){
        is String -> "String"
        is Int -> "int"
        is Double -> "double"
        is Char -> "char"
        else -> "unknwon"
    }
    println(result)

    //odd,even
    when{
        num1 % 2 == 1 -> println("odd")
        num1 % 2 == 0 -> println("even")
        else -> println("???")
    }

    println(getGrade(85))

    banbok();
    array()


    //클래스
    var animal = Animal("곰",3);
    var person = Person("saDdong",30);
//    person.age = 30;
//    person.name = "새똥이";

    println(animal)
    person.info()

    val student = Student(1,"kd")
    println(student)
    println(student.no)

    Obj.c++;
    Obj.c++;
    println(Obj.c);

    val a = fun() { println("hello A")}
    a()
    val b : ()  -> Unit ={println("Hello B")}
    b()
    val c : (Int) -> Int = {it + it};
    println(c(5));

    val d : (Int,Int) -> Int = {a,b -> a + b}
    println( d(5,3))
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
fun getGrade(score:Int): String =  when(score){
    in 90..100 -> "A"
    in 80 .. 89 -> "B"
    else -> "c"
}

fun banbok(){
    for (x in 2..9){
        println("=============${x}단===========")
        for (y in 1 ..9){
            println("${x} X ${y} = ${x * y}")
        }
    }


}
fun array(){
    //list imutable
    //new ArrayList(Stream.of(...).toList()) >> imutable List
    // mutableListof(1,2,3,"ac");

    //array
    val numbers = arrayOf(1,2,3,4,5)
    val number2 = Array(5){0}
    for(x in numbers){
        println(x)
    }
    println(number2.joinToString())
    println(number2[0])
    // List<int>
    intArrayOf()
    doubleArrayOf()
    //array -> index
    println(numbers[2])
    println(numbers.size)

    //numbers의 값 내부를 변경
    // 1번 인덱스의 값을 10으로
    // 3번인덱스의값을 20 으로 변경후 출력

    numbers[1] = 10;
    println(numbers.joinToString())
    numbers[3] = 20
    println(numbers.joinToString())
    numbers.withIndex()
    for((i,value)in numbers.withIndex()){
        println("i : ${i} ,value : ${value}" )
    }

    println(numbers.sortedArray().joinToString())
    println(numbers.sortedArrayDescending().joinToString())

    //map,sum
//    println( numbers.map { i -> i * i }.toIntArray())
    numbers.map { i -> i * i }.forEach { i ->  println(i) }


}
class Animal(
    var name:String,
    var count:Int
)
open class Person{
    var name: String =""
    var age = 0

    init {
        // 초기화 블록
    }
    constructor(name: String,age:Int){
        this.name = name
        this.age = age
    }

    fun info(){
        println("name : ${name} age : ${age}")
    }
}
//data 클래스는 data만 저장한다 (hashcode 자동생성 getterseter 자동생성)dto역할
data class Student (val no:Int,val name:String)

//singleton
object Obj{
    var c = 0;
}
class A {
	x : number;
}
class B {
	a : A;
	f() {
		this.a.x = 1;
		a.x = 99;
	}
}

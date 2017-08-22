package util

object Subsequence {
  @annotation.tailrec
  def startsWith[A](l: List[A], prefix: List[A]): Boolean = (l,prefix) match {
    case (_,Nil) => true
    case (h::t,h2::t2) if h == h2 => startsWith(t, t2)
    case _ => false
  }
  @annotation.tailrec
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
    case Nil => sub == Nil
    case _ if startsWith(sup, sub) => true
    case h::t => hasSubsequence(t, sub)
  }

  def subseqs[A](seq: Seq[A]): Seq[Seq[A]] = {
    subseqs(seq.length)(seq)
  }

  def subseqs[A](n: Int)(seq: Seq[A]): Seq[Seq[A]] = {
    1 to n flatMap seq.sliding
  }
}
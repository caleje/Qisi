package util

object Sequence {
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

  def startingSubseqs[A](seq: Seq[A]): Seq[Seq[A]] = {
    1 to seq.length map seq.take
  }

  def endingSubseqs[A](seq: Seq[A]): Seq[Seq[A]] = {
    seq.indices map seq.drop
  }

  private def insert[A](seq: Seq[A], i: Int, item: A): Seq[A] = {
    val (front, back) = seq.splitAt(i)
    (front :+ item) ++ back
  }
  def sequencesByInserting[A](seq: Seq[A], item: A): Seq[Seq[A]] = {
    (0 to seq.length).map(i => insert(seq, i, item))
  }

  def sequencesByRemoving[A](seq: Seq[A]): Seq[Seq[A]] = seq.indices.map(seq.patch(_, Nil, 1))

  def sequencesByPatching[A](seq: Seq[A], patch: A): Seq[Seq[A]] = seq.indices.map(seq.patch(_, Seq(patch), 1))
}
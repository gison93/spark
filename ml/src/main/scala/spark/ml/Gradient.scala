package spark.ml

import org.jblas.DoubleMatrix

abstract class Gradient extends Serializable {
  def compute(data: DoubleMatrix, label: Double, weights: DoubleMatrix): 
      (DoubleMatrix, Double)
}

class LogisticGradient extends Gradient {
  override def compute(data: DoubleMatrix, label: Double, weights: DoubleMatrix): 
      (DoubleMatrix, Double) = {
    val margin: Double = -1.0 * data.dot(weights)
    val gradientMultiplier = (1.0 / (1.0 + math.exp(margin))) - label

    val gradient = data.mul(gradientMultiplier)
    val loss = if (margin > 0) {
      math.log(1 + math.exp(0 - margin))
    } else {
      math.log(1 + math.exp(margin)) - margin
    }
    (gradient, loss)
  }
}

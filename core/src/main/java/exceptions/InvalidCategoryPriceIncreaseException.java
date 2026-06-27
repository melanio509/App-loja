package atec.poo.loja.core.exceptions;

public class InvalidCategoryPriceIncreaseException extends Exception {
  private double percentageIncrease;

  public InvalidCategoryPriceIncreaseException(double percentageIncrease) {
    this.percentageIncrease = percentageIncrease;
  }

  public double getPercentageIncrease() {
    return percentageIncrease;
  }
}

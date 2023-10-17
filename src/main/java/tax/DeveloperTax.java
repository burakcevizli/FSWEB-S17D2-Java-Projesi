package tax;

public class DeveloperTax implements Taxable{

    @Override
    public double getSimpleTaxRate() {
        return 0.15;
    }

    @Override
    public double getMiddleTaxRate() {
        return 0.2;
    }

    @Override
    public double getUpperTaxRate() {
        return 0.25;
    }
}

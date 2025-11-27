package be.brw.infrastructure;

import be.brw.domain.FitnessEvaluator;

public class RemoteFitnessEvaluator  implements FitnessEvaluator {
    @Override
    public int evaluate(java.util.List<Byte> genome) {
        try {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}

package mirari.model.unit.content

import mirari.vm.UnitVM
import mirari.vm.InnersHolderVM
import mirari.model.unit.inners.InnersHolder
import mirari.model.Unit

/**
 * @author alari
 * @since 4/7/12 12:10 AM
 */
class CompoundSchema {
    static public final CompoundSchema create() {
        new CompoundSchema()
    }

    private Map<String,Policy> policies = [:]
    private Map<String,Integer> required = [:]

    private class Policy {
        ContentPolicy policy
        int min
        int max
    }

    CompoundSchema required(ContentPolicy policy, Range<Integer> range) {
        final int from = (Integer)range.from
        policies.put(policy.name, new Policy(
                policy: policy,
                min: from,
                max: (Integer)range.to
        ))
        if(from > 0) {
            required.put(policy.name, from)
        }
        this
    }

    CompoundSchema required(ContentPolicy policy) {
        policies.put(policy.name, new Policy(
                policy: policy,
                min: 1,
                max: 1
        ))
        required.put(policy.name, 1)
        this
    }

    CompoundSchema required(ContentPolicy policy, int atLeast) {
        policies.put(policy.name, new Policy(
                policy: policy,
                min: atLeast,
                max: 0
        ))
        if(atLeast > 0) {
            required.put(policy.name, atLeast)
        }
        this
    }

    CompoundSchema maybe(ContentPolicy policy, int max=0) {
        policies.put(policy.name, new Policy(
                policy: policy,
                min: 0,
                max: max
        ))
        this
    }

    boolean canAttach(final InnersHolderVM viewModel, final UnitVM unit){
        final String type = unit.type
        if(!policies.containsKey(type)) return false;

        if(policies.get(type).max == 0) return true

        int count = policies.get(type).max
        for(UnitVM u in viewModel.inners) {
            if(u.type == type) --count
        }
        count > 0
    }

    boolean containsRequired(final InnersHolder holder){
        Map<String,Integer> counts = [:]
        for(Unit u : holder.inners) {
            if(!required.containsKey(u.contentPolicy.name)) continue;
            if(u.isEmpty()) continue;

            if(!counts.containsKey(u.contentPolicy.name)) {
                counts.put(u.contentPolicy.name, 1)
            } else {
                counts.put(u.contentPolicy.name, counts.get(u.contentPolicy.name)+1)
            }
        }
        for(String t in required.keySet()) {
            if(!counts.containsKey(t)) return false
            if(counts.get(t) < required.get(t)) return false;
        }
        true
    }
}

class RelationProperties {
    /*
     * Assuming that a two column array containing the relation and a one column          * array containing the set the relation is on is given in each method. 
     * No checks are performed.
     */
    //Useful help methods
	private static int indexOf(char c, char[] set){
		int index = -1;
		for (int i = 0;  i++ < set.length;) {
			if(c == set[i])index = i;
		}
		return index;
	}
	private static boolean hasNoLoops(char[][]rel){
		for (char[] r:rel) {
			//If it has at least one loop we know what we need to know
			//In isReflexive we investigate whether all nodes have loops
			if(r[0] == r[1])return false;
		}
		return true;
	}
	private static boolean hasDistictiveEdges(char[][] rel){
		for (char[]r:rel) {
			if(r[0] != r[1])return true;
		}
		return false;
	}
	 /**
	 * A relation is reflexive if every node has a loop
	 * @param relation The current relation
	 * @param set The set of elements in relation
	 * @return a boolean check of whether the amount of loops equals the amount of elements in the set
	 */
    public static boolean isReflexive(char[][] relation, char [] set){
    	int ctr = 0;
		for (int i = 0; i < relation.length; i++) {
			//Count loops from a node to itself ex.{b,b}
			if(relation[i][0] == relation[i][1]){
				ctr++;
			}
		}
		return ctr == set.length;
    }
	//It's symmetric if for all {a,b} we have a counterpart {b,azz}
    public static boolean isSymmetric(char[][] relation, char [] set){
		int amtOneway = 0;
		int amtBack = 0;
		for (int i = 0; i < relation.length;i++) {
			char[] curr = relation[i];
			if(curr[0] != curr[1]){
				amtOneway++;
				for (int j = 0; j < relation.length; j++) {
					if(relation[j][0] != relation[j][1]) {
						//If two nodes have edges going both ways, it's symmetric
						if(curr[0] == relation[j][1] && curr[1] == relation[j][0]){
							amtBack++;
						}
					}
				}
			}
		}
		//If we have same amt of edges one way as amt of edges the other way, we have symmetry
		return amtBack == amtOneway;
    }
    //char[] setA = {'a','x','r','m','2','0'};
	//If x --> y and y -->z, we also have x --> z
    public static boolean isTransitive(char[][] relation, char [] set){
    	boolean transitive = false;
		for (int i = 0; i < relation.length; i++) {
			transitive = true;
			char x1 = relation[i][0];
			char y1 = relation[i][1];
			if(x1 != y1){
				for (int j = 0; j < relation.length; j++) {
					char y2 = relation[j][0];
					char z1 = relation[j][1];
					if (y2 != z1) {
						//Does last corner match first corner of another elem
						if (y1 == y2 && z1 != x1) {
							int k = 0;
							transitive = false;
							while(k < relation.length && !transitive) {
								char x2 = relation[k][0];
								char z2 = relation[k][1];
								if(x2 != z2) {
									if (x1 == x2) {
										transitive = (z1 == z2);
									}
								}
								k++;
							}
						}
					}
				}
			}
		}
		//If a rel is symmetric, it must also be reflexive
		if(isSymmetric(relation, set)) {
			return isReflexive(relation, set) && transitive;
		}
		return transitive;
    }

	//Means that if we ex. have {a,b} and {b,a}, b = a, which means we only have loops and single way R
    public static boolean isAntiSymmetric(char[][] relation, char [] set){
		for (int i = 0; i < relation.length;i++) {
			char[] curr = relation[i];
			if(curr[0] != curr[1]){
				for (int j = 0; j < relation.length; j++) {
					if(relation[j][0] != relation[j][1]) {
						//If two nodes have edges going both ways, it's symmetric
						if(curr[0] == relation[j][1] && curr[1] == relation[j][0]){
							//We only need to find one symmetry to conclude that it isn't antiSymmetric
							return false;
						}
					}
				}
			}
		}
		return true;
    }
	//A relation is an Equivalence Relation if it's reflexive, symmetric and transitive.
    public static boolean isEquivalenceRelation(char[][] relation, char [] set){
		return isReflexive(relation,set) && isSymmetric(relation,set) && isTransitive(relation,set);
    }
	//A relation is a Partial order if it's transitive, antisymmetric and reflective
    public static boolean isPartialOrder(char[][] relation, char [] set){
		return isTransitive(relation,set) && isAntiSymmetric(relation, set) && isReflexive(relation, set);
    }

    public static void main(String[] args) {
    	char[] setA = {'a','x','r','m','2','0'};
		char[][] rel1 = {{'a','a'},{'r','a'},{'a','2'},{'x','x'},{'r','2'},{'r','r'},{'m','m'},{'2','r'},{'0','0'},{'a','r'},{'2','2'},{'2','a'}};
		char[][] rel2 = {{'a','x'},{'r','2'},{'0','0'},{'m','2'}};
		System.out.println("Rel1 is reflexive: " + isReflexive(rel1, setA));
		System.out.println("Rel2 is reflexive: " + isReflexive(rel2, setA));
		System.out.println("Rel1 is symmetric: " + isSymmetric(rel1, setA));
		System.out.println("Rel2 is symmetric: " + isSymmetric(rel2, setA));
		System.out.println("Rel1 is transitive: " + isTransitive(rel1, setA));
		System.out.println("Rel2 is transitive: " + isTransitive(rel2, setA));
		System.out.println("Rel1 is antisymmetric: " + isAntiSymmetric(rel1, setA));
		System.out.println("Rel2 is antisymmetric: " + isAntiSymmetric(rel2, setA));
		System.out.println("Rel1 is an equivalence relation: " + isEquivalenceRelation(rel1, setA));
		System.out.println("Rel2 is an equivalence relation: " + isEquivalenceRelation(rel2, setA));
		System.out.println("Rel1 is a partial order: " + isPartialOrder(rel1, setA));
		System.out.println("Rel2 is a partial order: " + isPartialOrder(rel2, setA));
		/* skal gi følgende utskrift:
		   Rel1 is reflexive: true
		   Rel2 is reflexive: false
		   Rel1 is symmetric: true
		   Rel2 is symmetric: false
		   Rel1 is transitive: true
		   Rel2 is transitive: true
		   Rel1 is antisymmetric: false
		   Rel2 is antisymmetric: true
		   Rel1 is an equivalence relation: true
		   Rel2 is an equivalence relation: false
		   Rel1 is a partial order: false
		   Rel2 is a partial order: false
		 */
    }


}

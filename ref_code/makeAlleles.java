    private static List<Allele> makeAlleles(Collection<Allele> alleles) {
        final List<Allele> alleleList = new ArrayList<Allele>(alleles.size());

        boolean sawRef = false;
        for ( final Allele a : alleles ) {
            for ( final Allele b : alleleList ) {
                if ( a.equals(b, true) )
                    throw new IllegalArgumentException("Duplicate allele added to VariantContext: " + a);
            }

            // deal with the case where the first allele isn't the reference
            if ( a.isReference() ) {
                if ( sawRef )
                    throw new IllegalArgumentException("Alleles for a VariantContext must contain at most one reference allele: " + alleles);
                alleleList.add(0, a);
                sawRef = true;
            }
            else
                alleleList.add(a);
        }

        if ( alleleList.isEmpty() )
            throw new IllegalArgumentException("Cannot create a VariantContext with an empty allele list");

        if ( alleleList.get(0).isNonReference() )
            throw new IllegalArgumentException("Alleles for a VariantContext must contain at least one reference allele: " + alleles);

        return alleleList;
    }


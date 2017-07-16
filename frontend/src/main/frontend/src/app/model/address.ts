export class Address {
    addrId?: number;
    addrCountry?: string;
    addrCity?: string;
    addr?: string;
    addrPhone?: string;
    postalCode?: string;
    addrRecvName?: string;

    constructor(input: any) {
        this.addrId = input.addrId || null;
        this.addrCountry = input.addrCountry || '';
        this.addrCity = input.addrCity || '';
        this.addr = input.addr || '';
        this.addrPhone = input.addrPhone || '';
        this.postalCode = input.postalCode || '';
        this.addrRecvName = input.addrRecvName || '';
    }
}
